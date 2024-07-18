package io.qifan.server.ai.tag.root.controller;

import cn.dev33.satoken.stp.StpUtil;
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
@RequestMapping("front/ai-tag")
@AllArgsConstructor
@DefaultFetcherOwner(AiTagRepository.class)
@Transactional
public class AiTagForFrontController {
    private final AiTagRepository aiTagRepository;

    @GetMapping("{id}")
    public @FetchBy(value = "COMPLEX_FETCHER_FOR_FRONT") AiTag findById(@PathVariable String id) {
        return aiTagRepository.findById(id, AiTagRepository.COMPLEX_FETCHER_FOR_FRONT).orElseThrow(() -> new BusinessException("数据不存在"));
    }

    @PostMapping("query")
    public Page<@FetchBy(value = "COMPLEX_FETCHER_FOR_FRONT") AiTag> query(@RequestBody QueryRequest<AiTagSpec> queryRequest) {
        queryRequest.getQuery().setCreatorId(StpUtil.getLoginIdAsString());
        return aiTagRepository.findPage(queryRequest, AiTagRepository.COMPLEX_FETCHER_FOR_FRONT);
    }

    @PostMapping
    public String create(@RequestBody @Validated AiTagCreateInput aiTagCreateInput) {
        return aiTagRepository.save(aiTagCreateInput).id();
    }

    @PutMapping
    public String update(@RequestBody @Validated AiTagUpdateInput aiTagUpdateInput) {
        AiTag aiTag = aiTagRepository.findById(aiTagUpdateInput.getId(), AiTagRepository.COMPLEX_FETCHER_FOR_FRONT).orElseThrow(() -> new BusinessException("数据不存在"));
        if (!aiTag.creator().id().equals(StpUtil.getLoginIdAsString())) {
            throw new BusinessException("只能修改自己的数据");
        }
        return aiTagRepository.save(aiTagUpdateInput).id();
    }

    @DeleteMapping
    public Boolean delete(@RequestBody List<String> ids) {
        aiTagRepository.findByIds(ids, AiTagRepository.COMPLEX_FETCHER_FOR_FRONT).forEach(aiTag -> {
            if (!aiTag.creator().id().equals(StpUtil.getLoginIdAsString())) {
                throw new BusinessException("只能删除自己的数据");
            }
        });
        aiTagRepository.deleteAllById(ids);
        return true;
    }
}
