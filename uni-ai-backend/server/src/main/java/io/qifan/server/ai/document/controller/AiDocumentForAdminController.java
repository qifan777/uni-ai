package io.qifan.server.ai.document.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import io.milvus.client.MilvusServiceClient;
import io.qifan.ai.dashscope.DashScopeAiChatModel;
import io.qifan.infrastructure.common.exception.BusinessException;
import io.qifan.server.ai.document.entity.AiDocument;
import io.qifan.server.ai.document.entity.AiDocumentDraft;
import io.qifan.server.ai.document.entity.dto.AiDocumentCreateInput;
import io.qifan.server.ai.document.entity.dto.AiDocumentSpec;
import io.qifan.server.ai.document.entity.dto.AiDocumentUpdateInput;
import io.qifan.server.ai.document.repository.AiDocumentRepository;
import io.qifan.server.ai.model.entity.AiModel;
import io.qifan.server.ai.model.entity.AiModelFetcher;
import io.qifan.server.ai.model.repository.AiModelRepository;
import io.qifan.server.ai.tag.root.entity.AiTag;
import io.qifan.server.ai.tag.root.entity.AiTagFetcher;
import io.qifan.server.ai.uni.chat.UniAiChatService;
import io.qifan.server.ai.uni.embedding.UniAiEmbeddingService;
import io.qifan.server.dict.model.DictConstants;
import io.qifan.server.infrastructure.model.QueryRequest;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.babyfish.jimmer.client.FetchBy;
import org.babyfish.jimmer.client.meta.DefaultFetcherOwner;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.MilvusVectorStore;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URL;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("admin/ai-document")
@AllArgsConstructor
@DefaultFetcherOwner(AiDocumentRepository.class)
@SaCheckPermission("/ai-document")
@Transactional
@Slf4j
public class AiDocumentForAdminController {
    private final AiDocumentRepository aiDocumentRepository;
    private final Map<String, UniAiEmbeddingService> embeddingServiceMap;
    private final Map<String, UniAiChatService> uniAiChatServiceMap;
    private final AiModelRepository aiModelRepository;
    private final MilvusServiceClient milvusServiceClient;
    private final DashScopeAiChatModel chatModel;

    @GetMapping("{id}")
    public @FetchBy(value = "COMPLEX_FETCHER_FOR_ADMIN") AiDocument findById(@PathVariable String id) {
        return aiDocumentRepository.findById(id, AiDocumentRepository.COMPLEX_FETCHER_FOR_ADMIN).orElseThrow(() -> new BusinessException("数据不存在"));
    }

    @PostMapping("query")
    public Page<@FetchBy(value = "COMPLEX_FETCHER_FOR_ADMIN") AiDocument> query(@RequestBody QueryRequest<AiDocumentSpec> queryRequest) {
        return aiDocumentRepository.findPage(queryRequest, AiDocumentRepository.COMPLEX_FETCHER_FOR_ADMIN);
    }

    @SneakyThrows
    @PostMapping
    public String create(@RequestBody @Validated AiDocumentCreateInput aiDocumentInput) {
        AiModel aiModel = aiModelRepository.findById(aiDocumentInput.getEmbeddingModelId(), AiModelFetcher.$
                        .allScalarFields()
                        .tagsView(AiTagFetcher.$.allScalarFields())).
                orElseThrow(() -> new BusinessException("模型不存在"));
        AiTag aiTag = aiModel.tagsView().stream().filter(tag -> tag.name().equals(DictConstants.AiModelTag.EMBEDDINGS))
                .findFirst()
                .orElseThrow(() -> new BusinessException("模型未配置Embeddings标签"));
        UniAiEmbeddingService uniAiEmbeddingService = embeddingServiceMap.get(StringUtils.uncapitalize(aiTag.springAiModel()));
        if (uniAiEmbeddingService == null) {
            throw new BusinessException("暂不支持该模型");
        }
        EmbeddingModel embeddingModel = uniAiEmbeddingService.getEmbeddingModel(aiModel.options());
        MilvusVectorStore milvusVectorStore = new MilvusVectorStore(milvusServiceClient, embeddingModel, true);
        TikaDocumentReader tikaDocumentReader = new TikaDocumentReader(new UrlResource(new URL(aiDocumentInput.getUrl())));
        List<Document> documents = tikaDocumentReader.get();
        List<Document> splitDocuments = new TokenTextSplitter().apply(documents);
        milvusVectorStore.add(splitDocuments);
        String summary = summary(String.join("", documents.stream().map(Document::getContent).toList()), aiDocumentInput.getSummaryModelId());
        return aiDocumentRepository.save(AiDocumentDraft.$.produce(aiDocumentInput.toEntity(), draft -> {
            draft.setSummary(summary);
        })).id();
    }

    public String summary(String content, String modelId) {
        AiModel aiModel = aiModelRepository.findById(modelId, AiModelFetcher.$
                        .allScalarFields()
                        .tagsView(AiTagFetcher.$.allScalarFields())).
                orElseThrow(() -> new BusinessException("模型不存在"));
        AiTag aiTag = aiModel.tagsView().stream().filter(tag -> tag.name().equals(DictConstants.AiModelTag.AIGC))
                .findFirst()
                .orElseThrow(() -> new BusinessException("模型未配置AIGC标签"));
        Message prompt = new PromptTemplate("请你总结下面的内容：{context}")
                .createMessage(Map.of("context", content));
        log.info("内容：{}", String.join("", content));
        UniAiChatService uniAiChatService = uniAiChatServiceMap.get(StringUtils.uncapitalize(aiTag.springAiModel()));
        if (uniAiChatService == null) {
            throw new BusinessException("暂不支持该模型");
        }
        String summary = uniAiChatService.getChatModel(aiModel.options())
                .call(new Prompt(prompt, uniAiChatService.getChatOptions(aiModel.options()))).getResult().getOutput().getContent();
        log.info("总结内容：{}", summary);
        return summary;
    }

    @PutMapping
    public String update(@RequestBody @Validated AiDocumentUpdateInput aiDocumentInput) {
        return aiDocumentRepository.save(aiDocumentInput).id();
    }

    @DeleteMapping
    public Boolean delete(@RequestBody List<String> ids) {
        aiDocumentRepository.deleteAllById(ids);
        return true;
    }
}