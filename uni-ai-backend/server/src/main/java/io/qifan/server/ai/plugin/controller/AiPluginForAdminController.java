package io.qifan.server.ai.plugin.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
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
@RequestMapping("admin/ai-plugin")
@AllArgsConstructor
@DefaultFetcherOwner(AiPluginRepository.class)
@SaCheckPermission("/ai-plugin")
@Transactional
public class AiPluginForAdminController {
    private final AiPluginRepository aiPluginRepository;

    @GetMapping("{id}")
    public @FetchBy(value = "COMPLEX_FETCHER_FOR_ADMIN") AiPlugin findById(@PathVariable String id) {
        return aiPluginRepository.findById(id, AiPluginRepository.COMPLEX_FETCHER_FOR_ADMIN).orElseThrow(() -> new BusinessException("数据不存在"));
    }

    @PostMapping("query")
    public Page<@FetchBy(value = "COMPLEX_FETCHER_FOR_ADMIN") AiPlugin> query(@RequestBody QueryRequest<AiPluginSpec> queryRequest) {
        return aiPluginRepository.findPage(queryRequest, AiPluginRepository.COMPLEX_FETCHER_FOR_ADMIN);
    }

    @PostMapping
    public String create(@RequestBody @Validated AiPluginCreateInput aiPluginInput) {
        return aiPluginRepository.save(aiPluginInput).id();
    }

    @PutMapping
    public String update(@RequestBody @Validated AiPluginUpdateInput aiPluginInput) {
        return aiPluginRepository.save(aiPluginInput).id();
    }

    @DeleteMapping
    public Boolean delete(@RequestBody List<String> ids) {
        aiPluginRepository.deleteAllById(ids);
        return true;
    }
}