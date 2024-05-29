package io.qifan.server.ai.role.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import io.qifan.infrastructure.common.exception.BusinessException;
import io.qifan.server.ai.role.entity.AiRole;
import io.qifan.server.ai.role.entity.dto.AiRoleCreateInput;
import io.qifan.server.ai.role.entity.dto.AiRoleSpec;
import io.qifan.server.ai.role.entity.dto.AiRoleUpdateInput;
import io.qifan.server.ai.role.repository.AiRoleRepository;
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
@RequestMapping("admin/ai-role")
@AllArgsConstructor
@DefaultFetcherOwner(AiRoleRepository.class)
@SaCheckPermission("/ai-role")
@Transactional
public class AiRoleForAdminController {
    private final AiRoleRepository aiRoleRepository;

    @GetMapping("{id}")
    public @FetchBy(value = "COMPLEX_FETCHER_FOR_ADMIN") AiRole findById(@PathVariable String id) {
        return aiRoleRepository.findById(id, AiRoleRepository.COMPLEX_FETCHER_FOR_ADMIN).orElseThrow(() -> new BusinessException("数据不存在"));
    }

    @PostMapping("query")
    public Page<@FetchBy(value = "COMPLEX_FETCHER_FOR_ADMIN") AiRole> query(@RequestBody QueryRequest<AiRoleSpec> queryRequest) {
        return aiRoleRepository.findPage(queryRequest, AiRoleRepository.COMPLEX_FETCHER_FOR_ADMIN);
    }

    @PostMapping
    public String create(@RequestBody @Validated AiRoleCreateInput aiRoleInput) {
        return aiRoleRepository.save(aiRoleInput).id();
    }

    @PutMapping
    public String update(@RequestBody @Validated AiRoleUpdateInput aiRoleInput) {
        return aiRoleRepository.save(aiRoleInput).id();
    }

    @DeleteMapping
    public Boolean delete(@RequestBody List<String> ids) {
        aiRoleRepository.deleteAllById(ids);
        return true;
    }
}