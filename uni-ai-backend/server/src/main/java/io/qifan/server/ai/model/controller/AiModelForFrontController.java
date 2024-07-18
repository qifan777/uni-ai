package io.qifan.server.ai.model.controller;

import cn.dev33.satoken.stp.StpUtil;
import io.qifan.infrastructure.common.exception.BusinessException;
import io.qifan.server.ai.model.entity.AiModel;
import io.qifan.server.ai.model.entity.dto.AiModelCreateInput;
import io.qifan.server.ai.model.entity.dto.AiModelSpec;
import io.qifan.server.ai.model.entity.dto.AiModelUpdateInput;
import io.qifan.server.ai.model.repository.AiModelRepository;
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
@RequestMapping("front/ai-model")
@AllArgsConstructor
@DefaultFetcherOwner(AiModelRepository.class)
@Transactional
public class AiModelForFrontController {
    private final AiModelRepository aiModelRepository;

    @GetMapping("{id}")
    public @FetchBy(value = "COMPLEX_FETCHER_FOR_FRONT") AiModel findById(@PathVariable String id) {
        return aiModelRepository.findById(id, AiModelRepository.COMPLEX_FETCHER_FOR_FRONT).orElseThrow(() -> new BusinessException("数据不存在"));
    }

    @PostMapping("query")
    public Page<@FetchBy(value = "COMPLEX_FETCHER_FOR_FRONT") AiModel> query(@RequestBody QueryRequest<AiModelSpec> queryRequest) {
        queryRequest.getQuery().setCreatorId(StpUtil.getLoginIdAsString());
        return aiModelRepository.findPage(queryRequest, AiModelRepository.COMPLEX_FETCHER_FOR_FRONT);
    }

    @PostMapping
    public String create(@RequestBody @Validated AiModelCreateInput aiModelCreateInput) {
        return aiModelRepository.save(aiModelCreateInput).id();
    }

    @PutMapping
    public String update(@RequestBody @Validated AiModelUpdateInput aiModelUpdateInput) {
        AiModel aiModel = aiModelRepository.findById(aiModelUpdateInput.getId(), AiModelRepository.COMPLEX_FETCHER_FOR_FRONT).orElseThrow(() -> new BusinessException("数据不存在"));
        if (!aiModel.creator().id().equals(StpUtil.getLoginIdAsString())) {
            throw new BusinessException("只能修改自己的数据");
        }
        return aiModelRepository.save(aiModelUpdateInput).id();
    }

    @DeleteMapping
    public Boolean delete(@RequestBody List<String> ids) {
        aiModelRepository.findByIds(ids, AiModelRepository.COMPLEX_FETCHER_FOR_FRONT).forEach(aiModel -> {
            if (!aiModel.creator().id().equals(StpUtil.getLoginIdAsString())) {
                throw new BusinessException("只能删除自己的数据");
            }
        });
        aiModelRepository.deleteAllById(ids);
        return true;
    }
}
