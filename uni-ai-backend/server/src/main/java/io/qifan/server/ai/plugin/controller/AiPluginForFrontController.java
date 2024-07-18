package io.qifan.server.ai.plugin.controller;

import cn.dev33.satoken.stp.StpUtil;
import io.qifan.infrastructure.common.exception.BusinessException;
import io.qifan.server.ai.plugin.entity.AiPlugin;
import io.qifan.server.ai.plugin.entity.dto.AiPluginCreateInput;
import io.qifan.server.ai.plugin.entity.dto.AiPluginSpec;
import io.qifan.server.ai.plugin.entity.dto.AiPluginUpdateInput;
import io.qifan.server.ai.plugin.repository.AiPluginRepository;
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
@RequestMapping("front/ai-plugin")
@AllArgsConstructor
@DefaultFetcherOwner(AiPluginRepository.class)
@Transactional
public class AiPluginForFrontController {
    private final AiPluginRepository aiPluginRepository;

    @GetMapping("{id}")
    public @FetchBy(value = "COMPLEX_FETCHER_FOR_FRONT") AiPlugin findById(@PathVariable String id) {
        return aiPluginRepository.findById(id, AiPluginRepository.COMPLEX_FETCHER_FOR_FRONT).orElseThrow(() -> new BusinessException("数据不存在"));
    }

    @PostMapping("query")
    public Page<@FetchBy(value = "COMPLEX_FETCHER_FOR_FRONT") AiPlugin> query(@RequestBody QueryRequest<AiPluginSpec> queryRequest) {
        queryRequest.getQuery().setCreatorId(StpUtil.getLoginIdAsString());
        return aiPluginRepository.findPage(queryRequest, AiPluginRepository.COMPLEX_FETCHER_FOR_FRONT);
    }

    @PostMapping
    public String create(@RequestBody @Validated AiPluginCreateInput aiPluginCreateInput) {
        return aiPluginRepository.save(aiPluginCreateInput).id();
    }

    @PutMapping
    public String update(@RequestBody @Validated AiPluginUpdateInput aiPluginUpdateInput) {
        AiPlugin aiPlugin = aiPluginRepository.findById(aiPluginUpdateInput.getId(), AiPluginRepository.COMPLEX_FETCHER_FOR_FRONT).orElseThrow(() -> new BusinessException("数据不存在"));
        if (!aiPlugin.creator().id().equals(StpUtil.getLoginIdAsString())) {
            throw new BusinessException("只能修改自己的数据");
        }
        return aiPluginRepository.save(aiPluginUpdateInput).id();
    }

    @DeleteMapping
    public Boolean delete(@RequestBody List<String> ids) {
        aiPluginRepository.findByIds(ids, AiPluginRepository.COMPLEX_FETCHER_FOR_FRONT).forEach(aiPlugin -> {
            if (!aiPlugin.creator().id().equals(StpUtil.getLoginIdAsString())) {
                throw new BusinessException("只能删除自己的数据");
            }
        });
        aiPluginRepository.deleteAllById(ids);
        return true;
    }
}
