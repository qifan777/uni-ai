
package io.qifan.server.ai.document.controller;
import cn.dev33.satoken.stp.StpUtil;
import io.qifan.infrastructure.common.exception.BusinessException;
import io.qifan.server.ai.document.entity.AiDocument;
import io.qifan.server.ai.document.entity.dto.AiDocumentCreateInput;
import io.qifan.server.ai.document.entity.dto.AiDocumentSpec;
import io.qifan.server.ai.document.entity.dto.AiDocumentUpdateInput;
import io.qifan.server.ai.document.repository.AiDocumentRepository;
import io.qifan.server.infrastructure.model.QueryRequest;
import lombok.AllArgsConstructor;
import org.babyfish.jimmer.client.FetchBy;
import org.babyfish.jimmer.client.meta.DefaultFetcherOwner;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("front/ai-document")
@AllArgsConstructor
@DefaultFetcherOwner(AiDocumentRepository.class)
@Transactional
public class AiDocumentForFrontController {
    private final AiDocumentRepository aiDocumentRepository;

    @GetMapping("{id}")
    public @FetchBy(value = "COMPLEX_FETCHER_FOR_FRONT") AiDocument findById(@PathVariable String id) {
        return aiDocumentRepository.findById(id, AiDocumentRepository.COMPLEX_FETCHER_FOR_FRONT).orElseThrow(() -> new BusinessException("数据不存在"));
    }

    @PostMapping("query")
    public Page< @FetchBy(value = "COMPLEX_FETCHER_FOR_FRONT") AiDocument> query(@RequestBody QueryRequest<AiDocumentSpec> queryRequest) {
    queryRequest.getQuery().setCreatorId(StpUtil.getLoginIdAsString());
    return aiDocumentRepository.findPage(queryRequest, AiDocumentRepository.COMPLEX_FETCHER_FOR_FRONT);
}

    @PostMapping
    public String create(@RequestBody @Validated AiDocumentCreateInput aiDocumentCreateInput) {
        return aiDocumentRepository.save(aiDocumentCreateInput).id();
    }

    @PutMapping
    public String update(@RequestBody @Validated AiDocumentUpdateInput aiDocumentUpdateInput) {
        AiDocument aiDocument = aiDocumentRepository.findById(aiDocumentUpdateInput.getId(), AiDocumentRepository.COMPLEX_FETCHER_FOR_FRONT).orElseThrow(() -> new BusinessException("数据不存在"));
        if (!aiDocument.creator().id().equals(StpUtil.getLoginIdAsString())) {
            throw new BusinessException("只能修改自己的数据");
        }
        return aiDocumentRepository.save(aiDocumentUpdateInput).id();
    }

    @DeleteMapping
    public Boolean delete(@RequestBody List<String> ids) {
        aiDocumentRepository.findByIds(ids, AiDocumentRepository.COMPLEX_FETCHER_FOR_FRONT).forEach(aiDocument -> {
            if (!aiDocument.creator().id().equals(StpUtil.getLoginIdAsString())) {
                throw new BusinessException("只能删除自己的数据");
            }
        });
        aiDocumentRepository.deleteAllById(ids);
        return true;
    }
}
