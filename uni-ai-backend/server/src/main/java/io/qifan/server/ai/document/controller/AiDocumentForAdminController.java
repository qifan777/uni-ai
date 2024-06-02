package io.qifan.server.ai.document.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import io.qifan.infrastructure.common.exception.BusinessException;
import io.qifan.server.ai.document.entity.AiDocument;
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
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URL;
import java.util.List;

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
        TikaDocumentReader tikaDocumentReader = new TikaDocumentReader(new UrlResource(new URL(aiDocumentInput.getUrl())));
        List<Document> documents = tikaDocumentReader.get();
        List<Document> splitDocuments = new TokenTextSplitter().apply(documents);
        uniAiVectorService.embedding(splitDocuments, aiDocumentInput.getAiCollectionId());
        return aiDocumentRepository.save(aiDocumentInput.toEntity()).id();
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