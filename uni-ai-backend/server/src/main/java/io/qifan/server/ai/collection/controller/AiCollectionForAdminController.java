package io.qifan.server.ai.collection.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import io.qifan.infrastructure.common.exception.BusinessException;
import io.qifan.server.ai.collection.entity.AiCollection;
import io.qifan.server.ai.collection.entity.dto.AiCollectionCreateInput;
import io.qifan.server.ai.collection.entity.dto.AiCollectionSpec;
import io.qifan.server.ai.collection.entity.dto.AiCollectionUpdateInput;
import io.qifan.server.ai.collection.repository.AiCollectionRepository;
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
@RequestMapping("admin/ai-collection")
@AllArgsConstructor
@DefaultFetcherOwner(AiCollectionRepository.class)
@SaCheckPermission("/ai-collection")
@Transactional
public class AiCollectionForAdminController {
    private final AiCollectionRepository aiCollectionRepository;

    @GetMapping("{id}")
    public @FetchBy(value = "COMPLEX_FETCHER_FOR_ADMIN") AiCollection findById(@PathVariable String id) {
        return aiCollectionRepository.findById(id, AiCollectionRepository.COMPLEX_FETCHER_FOR_ADMIN).orElseThrow(() -> new BusinessException("数据不存在"));
    }

    @PostMapping("query")
    public Page<@FetchBy(value = "COMPLEX_FETCHER_FOR_ADMIN") AiCollection> query(@RequestBody QueryRequest<AiCollectionSpec> queryRequest) {
        return aiCollectionRepository.findPage(queryRequest, AiCollectionRepository.COMPLEX_FETCHER_FOR_ADMIN);
    }

    @PostMapping
    public String create(@RequestBody @Validated AiCollectionCreateInput aiCollectionInput) {
        return aiCollectionRepository.save(aiCollectionInput).id();
    }

    @PutMapping
    public String update(@RequestBody @Validated AiCollectionUpdateInput aiCollectionInput) {
        return aiCollectionRepository.save(aiCollectionInput).id();
    }

    @DeleteMapping
    public Boolean delete(@RequestBody List<String> ids) {
        aiCollectionRepository.deleteAllById(ids);
        return true;
    }
}