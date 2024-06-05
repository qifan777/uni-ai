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
import lombok.extern.slf4j.Slf4j;
import org.babyfish.jimmer.client.FetchBy;
import org.babyfish.jimmer.client.meta.DefaultFetcherOwner;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    public String create(@RequestBody @Validated AiDocumentCreateInput aiDocumentCreateInput) {
        return aiDocumentRepository.save(aiDocumentCreateInput).id();
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