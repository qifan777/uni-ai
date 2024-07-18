package io.qifan.server.ai.collection.controller;

import cn.dev33.satoken.stp.StpUtil;
import io.qifan.infrastructure.common.exception.BusinessException;
import io.qifan.server.ai.collection.entity.AiCollection;
import io.qifan.server.ai.collection.entity.dto.AiCollectionCreateInput;
import io.qifan.server.ai.collection.entity.dto.AiCollectionSpec;
import io.qifan.server.ai.collection.entity.dto.AiCollectionUpdateInput;
import io.qifan.server.ai.collection.repository.AiCollectionRepository;
import io.qifan.server.ai.uni.vector.UniAiVectorService;
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
@RequestMapping("front/ai-collection")
@AllArgsConstructor
@DefaultFetcherOwner(AiCollectionRepository.class)
@Transactional
public class AiCollectionForFrontController {
    private final AiCollectionRepository aiCollectionRepository;
    private final UniAiVectorService uniAiVectorService;

    @GetMapping("{id}")
    public @FetchBy(value = "COMPLEX_FETCHER_FOR_FRONT") AiCollection findById(@PathVariable String id) {
        return aiCollectionRepository.findById(id, AiCollectionRepository.COMPLEX_FETCHER_FOR_FRONT).orElseThrow(() -> new BusinessException("数据不存在"));
    }

    @PostMapping("query")
    public Page<@FetchBy(value = "COMPLEX_FETCHER_FOR_FRONT") AiCollection> query(@RequestBody QueryRequest<AiCollectionSpec> queryRequest) {
        queryRequest.getQuery().setCreatorId(StpUtil.getLoginIdAsString());
        return aiCollectionRepository.findPage(queryRequest, AiCollectionRepository.COMPLEX_FETCHER_FOR_FRONT);
    }

    @PostMapping
    public String create(@RequestBody @Validated AiCollectionCreateInput aiCollectionCreateInput) {
        return aiCollectionRepository.save(aiCollectionCreateInput).id();
    }

    @PutMapping
    public String update(@RequestBody @Validated AiCollectionUpdateInput aiCollectionUpdateInput) {
        AiCollection aiCollection = aiCollectionRepository.findById(aiCollectionUpdateInput.getId(), AiCollectionRepository.COMPLEX_FETCHER_FOR_FRONT).orElseThrow(() -> new BusinessException("数据不存在"));
        if (!aiCollection.creator().id().equals(StpUtil.getLoginIdAsString())) {
            throw new BusinessException("只能修改自己的数据");
        }
        return aiCollectionRepository.save(aiCollectionUpdateInput).id();
    }

    @DeleteMapping
    public Boolean delete(@RequestBody List<String> ids) {
        List<AiCollection> collections = aiCollectionRepository.findByIds(ids, AiCollectionRepository.COMPLEX_FETCHER_FOR_FRONT);
        collections.forEach(aiCollection -> {
            if (!aiCollection.creator().id().equals(StpUtil.getLoginIdAsString())) {
                throw new BusinessException("只能删除自己的数据");
            }
        });
        collections.forEach(aiCollection -> {
            uniAiVectorService.deleteCollection(aiCollection.collectionName());
        });
        aiCollectionRepository.deleteAllById(ids);
        return true;
    }
}
