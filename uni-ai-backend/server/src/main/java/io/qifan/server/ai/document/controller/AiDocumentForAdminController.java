package io.qifan.server.ai.document.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.core.util.IdUtil;
import io.qifan.infrastructure.common.exception.BusinessException;
import io.qifan.server.ai.document.entity.AiDocument;
import io.qifan.server.ai.document.entity.AiDocumentDraft;
import io.qifan.server.ai.document.entity.dto.AiDocumentCreateInput;
import io.qifan.server.ai.document.entity.dto.AiDocumentSpec;
import io.qifan.server.ai.document.entity.dto.AiDocumentUpdateInput;
import io.qifan.server.ai.document.repository.AiDocumentRepository;
import io.qifan.server.ai.uni.vector.UniAiVectorService;
import io.qifan.server.infrastructure.model.QueryRequest;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.babyfish.jimmer.client.FetchBy;
import org.babyfish.jimmer.client.meta.DefaultFetcherOwner;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    private final UniAiVectorService uniAiVectorService;

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
        List<Document> splitDocuments = new TokenTextSplitter().apply(List.of(new Document(IdUtil.fastSimpleUUID(), aiDocumentInput.getContent(), null, Map.of())));
        uniAiVectorService.embedding(splitDocuments, aiDocumentInput.getAiCollectionId());
        return aiDocumentRepository.save(AiDocumentDraft.$.produce(aiDocumentInput.toEntity(), draft -> {
                    draft.setDocIds(splitDocuments.stream().map(Document::getId).toList());
                }))
                .id();
    }

    @SneakyThrows
    @PostMapping("extract")
    public String extract(@RequestParam MultipartFile file) {
        TikaDocumentReader tikaDocumentReader = new TikaDocumentReader(new InputStreamResource(file.getInputStream()));
        return tikaDocumentReader.read().get(0).getContent();
    }

    @PutMapping
    public String update(@RequestBody @Validated AiDocumentUpdateInput aiDocumentInput) {
        return aiDocumentRepository.save(aiDocumentInput).id();
    }

    @DeleteMapping
    public Boolean delete(@RequestBody List<String> ids) {
        List<AiDocument> documents = aiDocumentRepository.findByIds(ids);
        documents.forEach(aiDocument -> {
            uniAiVectorService.getVectorStore(aiDocument.aiCollectionId())
                    .delete(aiDocument.docIds());
        });
        aiDocumentRepository.deleteAllById(ids);
        return true;
    }
}