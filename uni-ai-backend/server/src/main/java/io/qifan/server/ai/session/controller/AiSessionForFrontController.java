
package io.qifan.server.ai.session.controller;
import cn.dev33.satoken.annotation.SaCheckDisable;
import cn.dev33.satoken.stp.StpUtil;
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
@RequestMapping("front/ai-session")
@AllArgsConstructor
@DefaultFetcherOwner(AiSessionRepository.class)
@Transactional
@SaCheckDisable
public class AiSessionForFrontController {
    private final AiSessionRepository aiSessionRepository;

    @GetMapping("{id}")
    public @FetchBy(value = "COMPLEX_FETCHER_FOR_FRONT") AiSession findById(@PathVariable String id) {
        return aiSessionRepository.findById(id, AiSessionRepository.COMPLEX_FETCHER_FOR_FRONT).orElseThrow(() -> new BusinessException("数据不存在"));
    }

    @PostMapping("query")
    public Page< @FetchBy(value = "COMPLEX_FETCHER_FOR_FRONT") AiSession> query(@RequestBody QueryRequest<AiSessionSpec> queryRequest) {
    queryRequest.getQuery().setCreatorId(StpUtil.getLoginIdAsString());
    return aiSessionRepository.findPage(queryRequest, AiSessionRepository.COMPLEX_FETCHER_FOR_FRONT);
}

    @PostMapping
    public String create(@RequestBody @Validated AiSessionCreateInput aiSessionCreateInput) {
        return aiSessionRepository.save(aiSessionCreateInput).id();
    }

    @PutMapping
    public String update(@RequestBody @Validated AiSessionUpdateInput aiSessionUpdateInput) {
        AiSession aiSession = aiSessionRepository.findById(aiSessionUpdateInput.getId(), AiSessionRepository.COMPLEX_FETCHER_FOR_FRONT).orElseThrow(() -> new BusinessException("数据不存在"));
        if (!aiSession.creator().id().equals(StpUtil.getLoginIdAsString())) {
            throw new BusinessException("只能修改自己的数据");
        }
        return aiSessionRepository.save(aiSessionUpdateInput).id();
    }

    @DeleteMapping
    public Boolean delete(@RequestBody List<String> ids) {
        aiSessionRepository.findByIds(ids, AiSessionRepository.COMPLEX_FETCHER_FOR_FRONT).forEach(aiSession -> {
            if (!aiSession.creator().id().equals(StpUtil.getLoginIdAsString())) {
                throw new BusinessException("只能删除自己的数据");
            }
        });
        aiSessionRepository.deleteAllById(ids);
        return true;
    }
}
