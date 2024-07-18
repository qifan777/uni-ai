package io.qifan.server.ai.factory.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import io.qifan.infrastructure.common.exception.BusinessException;
import io.qifan.server.ai.factory.entity.AiFactory;
import io.qifan.server.ai.factory.entity.dto.AiFactoryCreateInput;
import io.qifan.server.ai.factory.entity.dto.AiFactorySpec;
import io.qifan.server.ai.factory.entity.dto.AiFactoryUpdateInput;
import io.qifan.server.ai.factory.repository.AiFactoryRepository;
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
@RequestMapping("admin/ai-factory")
@AllArgsConstructor
@DefaultFetcherOwner(AiFactoryRepository.class)
@SaCheckPermission("/ai-factory")
@Transactional
public class AiFactoryForAdminController {
    private final AiFactoryRepository aiFactoryRepository;

    @GetMapping("{id}")
    public @FetchBy(value = "COMPLEX_FETCHER_FOR_ADMIN") AiFactory findById(@PathVariable String id) {
        return aiFactoryRepository.findById(id, AiFactoryRepository.COMPLEX_FETCHER_FOR_ADMIN).orElseThrow(() -> new BusinessException("数据不存在"));
    }

    @PostMapping("query")
    public Page<@FetchBy(value = "COMPLEX_FETCHER_FOR_ADMIN") AiFactory> query(@RequestBody QueryRequest<AiFactorySpec> queryRequest) {
        return aiFactoryRepository.findPage(queryRequest, AiFactoryRepository.COMPLEX_FETCHER_FOR_ADMIN);
    }

    @PostMapping
    public String create(@RequestBody @Validated AiFactoryCreateInput aiFactoryInput) {
        return aiFactoryRepository.save(aiFactoryInput).id();
    }

    @PutMapping
    public String update(@RequestBody @Validated AiFactoryUpdateInput aiFactoryInput) {
        return aiFactoryRepository.save(aiFactoryInput).id();
    }

    @DeleteMapping
    public Boolean delete(@RequestBody List<String> ids) {
        aiFactoryRepository.deleteAllById(ids);
        return true;
    }
}