package io.qifan.server.ai.model.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import io.qifan.infrastructure.common.exception.BusinessException;
import io.qifan.server.ai.model.entity.AiModel;
import io.qifan.server.ai.model.entity.dto.AiModelCreateInput;
import io.qifan.server.ai.model.entity.dto.AiModelSpec;
import io.qifan.server.ai.model.entity.dto.AiModelUpdateInput;
import io.qifan.server.ai.model.repository.AiModelRepository;
import io.qifan.server.ai.tag.model.entity.AiModelTagRel;
import io.qifan.server.ai.tag.model.entity.AiModelTagRelDraft;
import io.qifan.server.ai.tag.model.entity.AiModelTagRelTable;
import io.qifan.server.ai.tag.model.repository.AiTagModelRelRepository;
import io.qifan.server.infrastructure.model.QueryRequest;
import lombok.AllArgsConstructor;
import org.babyfish.jimmer.client.FetchBy;
import org.babyfish.jimmer.client.meta.DefaultFetcherOwner;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("admin/ai-model")
@AllArgsConstructor
@DefaultFetcherOwner(AiModelRepository.class)
@SaCheckPermission("/ai-model")
@Transactional
public class AiModelForAdminController {
    private final AiModelRepository aiModelRepository;
    private final AiTagModelRelRepository aiTagModelRelRepository;

    @GetMapping("{id}")
    public @FetchBy(value = "COMPLEX_FETCHER_FOR_ADMIN") AiModel findById(@PathVariable String id) {
        return aiModelRepository.findById(id, AiModelRepository.COMPLEX_FETCHER_FOR_ADMIN).orElseThrow(() -> new BusinessException("数据不存在"));
    }

    @PostMapping("query")
    public Page<@FetchBy(value = "COMPLEX_FETCHER_FOR_ADMIN") AiModel> query(@RequestBody QueryRequest<AiModelSpec> queryRequest) {
        return aiModelRepository.findPage(queryRequest, AiModelRepository.COMPLEX_FETCHER_FOR_ADMIN);
    }

    @PostMapping
    public String create(@RequestBody @Validated AiModelCreateInput aiModelInput) {
        String id = aiModelRepository.save(aiModelInput).id();
        saveTags(Arrays.asList(aiModelInput.getTagIds()), id);
        return id;
    }

    public void saveTags(List<String> tagIds, String modelId) {
        AiModelTagRelTable t = AiModelTagRelTable.$;
        aiTagModelRelRepository.sql().createDelete(t)
                .where(t.aiModelId().eq(modelId))
                .execute();
        List<AiModelTagRel> list = tagIds.stream().map(tagId -> {
            return AiModelTagRelDraft.$.produce(draft -> {
                draft.setAiTagId(tagId)
                        .setAiModelId(modelId);
            });
        }).toList();
        aiTagModelRelRepository.saveEntities(list);
    }

    @PutMapping
    public String update(@RequestBody @Validated AiModelUpdateInput aiModelInput) {
        String id = aiModelRepository.save(aiModelInput).id();
        saveTags(Arrays.asList(aiModelInput.getTagIds()), id);
        return id;
    }

    @DeleteMapping
    public Boolean delete(@RequestBody List<String> ids) {
        aiModelRepository.deleteAllById(ids);
        return true;
    }
}