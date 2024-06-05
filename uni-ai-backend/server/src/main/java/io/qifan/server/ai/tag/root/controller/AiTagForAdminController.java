package io.qifan.server.ai.tag.root.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaIgnore;
import io.qifan.infrastructure.common.exception.BusinessException;
import io.qifan.server.ai.tag.root.entity.AiTag;
import io.qifan.server.ai.tag.root.entity.dto.AiTagCreateInput;
import io.qifan.server.ai.tag.root.entity.dto.AiTagSpec;
import io.qifan.server.ai.tag.root.entity.dto.AiTagUpdateInput;
import io.qifan.server.ai.tag.root.repository.AiTagRepository;
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
@RequestMapping("admin/ai-tag")
@AllArgsConstructor
@DefaultFetcherOwner(AiTagRepository.class)
@SaCheckPermission("/ai-tag")
@Transactional
public class AiTagForAdminController {
    private final AiTagRepository aiTagRepository;

    @SaIgnore
    @GetMapping("{id}")
    public @FetchBy(value = "COMPLEX_FETCHER_FOR_ADMIN") AiTag findById(@PathVariable String id) {
        return aiTagRepository.findById(id, AiTagRepository.COMPLEX_FETCHER_FOR_ADMIN).orElseThrow(() -> new BusinessException("数据不存在"));
    }

    @SaIgnore
    @PostMapping("query")
    public Page<@FetchBy(value = "COMPLEX_FETCHER_FOR_ADMIN") AiTag> query(@RequestBody QueryRequest<AiTagSpec> queryRequest) {
        return aiTagRepository.findPage(queryRequest, AiTagRepository.COMPLEX_FETCHER_FOR_ADMIN);
    }

    @PostMapping
    public String create(@RequestBody @Validated AiTagCreateInput aiTagInput) {
        return aiTagRepository.save(aiTagInput).id();
    }

    @PutMapping
    public String update(@RequestBody @Validated AiTagUpdateInput aiTagInput) {
        return aiTagRepository.save(aiTagInput).id();
    }

    @DeleteMapping
    public Boolean delete(@RequestBody List<String> ids) {
        aiTagRepository.deleteAllById(ids);
        return true;
    }
}