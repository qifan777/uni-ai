package io.qifan.server.ai.session.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import io.qifan.infrastructure.common.exception.BusinessException;
import io.qifan.server.ai.session.entity.AiSession;
import io.qifan.server.ai.session.entity.dto.AiSessionCreateInput;
import io.qifan.server.ai.session.entity.dto.AiSessionSpec;
import io.qifan.server.ai.session.entity.dto.AiSessionUpdateInput;
import io.qifan.server.ai.session.repository.AiSessionRepository;
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
@RequestMapping("admin/ai-session")
@AllArgsConstructor
@DefaultFetcherOwner(AiSessionRepository.class)
@SaCheckPermission("/ai-session")
@Transactional
public class AiSessionForAdminController {
    private final AiSessionRepository aiSessionRepository;

    @GetMapping("{id}")
    public @FetchBy(value = "COMPLEX_FETCHER_FOR_ADMIN") AiSession findById(@PathVariable String id) {
        return aiSessionRepository.findById(id, AiSessionRepository.COMPLEX_FETCHER_FOR_ADMIN).orElseThrow(() -> new BusinessException("数据不存在"));
    }

    @PostMapping("query")
    public Page<@FetchBy(value = "COMPLEX_FETCHER_FOR_ADMIN") AiSession> query(@RequestBody QueryRequest<AiSessionSpec> queryRequest) {
        return aiSessionRepository.findPage(queryRequest, AiSessionRepository.COMPLEX_FETCHER_FOR_ADMIN);
    }

    @PostMapping
    public String create(@RequestBody @Validated AiSessionCreateInput aiSessionInput) {
        return aiSessionRepository.save(aiSessionInput).id();
    }

    @PutMapping
    public String update(@RequestBody @Validated AiSessionUpdateInput aiSessionInput) {
        return aiSessionRepository.save(aiSessionInput).id();
    }

    @DeleteMapping
    public Boolean delete(@RequestBody List<String> ids) {
        aiSessionRepository.deleteAllById(ids);
        return true;
    }
}