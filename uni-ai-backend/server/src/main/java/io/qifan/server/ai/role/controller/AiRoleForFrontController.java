package io.qifan.server.ai.role.controller;

import cn.dev33.satoken.stp.StpUtil;
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
@RequestMapping("front/ai-role")
@AllArgsConstructor
@DefaultFetcherOwner(AiRoleRepository.class)
@Transactional
public class AiRoleForFrontController {
    private final AiRoleRepository aiRoleRepository;

    @GetMapping("{id}")
    public @FetchBy(value = "COMPLEX_FETCHER_FOR_FRONT") AiRole findById(@PathVariable String id) {
        return aiRoleRepository.findById(id, AiRoleRepository.COMPLEX_FETCHER_FOR_FRONT).orElseThrow(() -> new BusinessException("数据不存在"));
    }

    @PostMapping("query")
    public Page<@FetchBy(value = "COMPLEX_FETCHER_FOR_FRONT") AiRole> query(@RequestBody QueryRequest<AiRoleSpec> queryRequest) {
        return aiRoleRepository.findPage(queryRequest, AiRoleRepository.COMPLEX_FETCHER_FOR_FRONT);
    }

    @PostMapping
    public String create(@RequestBody @Validated AiRoleCreateInput aiRoleCreateInput) {
        return aiRoleRepository.save(aiRoleCreateInput).id();
    }

    @PutMapping
    public String update(@RequestBody @Validated AiRoleUpdateInput aiRoleUpdateInput) {
        AiRole aiRole = aiRoleRepository.findById(aiRoleUpdateInput.getId(), AiRoleRepository.COMPLEX_FETCHER_FOR_FRONT).orElseThrow(() -> new BusinessException("数据不存在"));
        if (!aiRole.creator().id().equals(StpUtil.getLoginIdAsString())) {
            throw new BusinessException("只能修改自己的数据");
        }
        return aiRoleRepository.save(aiRoleUpdateInput).id();
    }

    @DeleteMapping
    public Boolean delete(@RequestBody List<String> ids) {
        aiRoleRepository.findByIds(ids, AiRoleRepository.COMPLEX_FETCHER_FOR_FRONT).forEach(aiRole -> {
            if (!aiRole.creator().id().equals(StpUtil.getLoginIdAsString())) {
                throw new BusinessException("只能删除自己的数据");
            }
        });
        aiRoleRepository.deleteAllById(ids);
        return true;
    }
}
