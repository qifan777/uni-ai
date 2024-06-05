
package io.qifan.server.ai.factory.controller;

import cn.dev33.satoken.stp.StpUtil;
import io.qifan.infrastructure.common.exception.BusinessException;
import io.qifan.server.ai.factory.entity.AiFactory;
import io.qifan.server.ai.factory.entity.AiFactoryFetcher;
import io.qifan.server.ai.factory.entity.AiFactoryTable;
import io.qifan.server.ai.factory.entity.dto.AiFactoryCreateInput;
import io.qifan.server.ai.factory.entity.dto.AiFactorySpec;
import io.qifan.server.ai.factory.entity.dto.AiFactoryUpdateInput;
import io.qifan.server.ai.factory.repository.AiFactoryRepository;
import io.qifan.server.infrastructure.model.QueryRequest;
import io.qifan.server.user.root.entity.User;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.babyfish.jimmer.client.FetchBy;
import org.babyfish.jimmer.client.meta.DefaultFetcherOwner;
import org.babyfish.jimmer.sql.event.EntityEvent;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("front/ai-factory")
@AllArgsConstructor
@DefaultFetcherOwner(AiFactoryRepository.class)
@Transactional
public class AiFactoryForFrontController {
    private final AiFactoryRepository aiFactoryRepository;


    @GetMapping("{id}")
    public @FetchBy(value = "COMPLEX_FETCHER_FOR_FRONT") AiFactory findById(@PathVariable String id) {
        return aiFactoryRepository.findById(id, AiFactoryRepository.COMPLEX_FETCHER_FOR_FRONT).orElseThrow(() -> new BusinessException("数据不存在"));
    }

    @PostMapping("query")
    public Page<@FetchBy(value = "COMPLEX_FETCHER_FOR_FRONT") AiFactory> query(@RequestBody QueryRequest<AiFactorySpec> queryRequest) {
        queryRequest.getQuery().setCreatorId(StpUtil.getLoginIdAsString());
        return aiFactoryRepository.findPage(queryRequest, AiFactoryRepository.COMPLEX_FETCHER_FOR_FRONT);
    }

    @PostMapping
    public String create(@RequestBody @Validated AiFactoryCreateInput aiFactoryCreateInput) {
        return aiFactoryRepository.save(aiFactoryCreateInput).id();
    }

    @PutMapping
    public String update(@RequestBody @Validated AiFactoryUpdateInput aiFactoryUpdateInput) {
        AiFactory aiFactory = aiFactoryRepository.findById(aiFactoryUpdateInput.getId(), AiFactoryRepository.COMPLEX_FETCHER_FOR_FRONT).orElseThrow(() -> new BusinessException("数据不存在"));
        if (!aiFactory.creator().id().equals(StpUtil.getLoginIdAsString())) {
            throw new BusinessException("只能修改自己的数据");
        }
        return aiFactoryRepository.save(aiFactoryUpdateInput).id();
    }

    @DeleteMapping
    public Boolean delete(@RequestBody List<String> ids) {
        aiFactoryRepository.findByIds(ids, AiFactoryRepository.COMPLEX_FETCHER_FOR_FRONT).forEach(aiFactory -> {
            if (!aiFactory.creator().id().equals(StpUtil.getLoginIdAsString())) {
                throw new BusinessException("只能删除自己的数据");
            }
        });
        aiFactoryRepository.deleteAllById(ids);
        return true;
    }

    @PostConstruct
    public void init() {
        aiFactoryRepository.sql().getTriggers().addEntityListener(User.class, e -> {
            if (e.getType().equals(EntityEvent.Type.INSERT)) {
                StpUtil.switchTo(e.getId());
                AiFactoryTable t = AiFactoryTable.$;
                List<AiFactory> aiFactories = aiFactoryRepository.sql().createQuery(t)
                        .where(t.creatorId().eq("dcd256e2412f4162a6a5fcbd5cfedc84"))
                        .select(t.fetch(AiFactoryFetcher.$.name().description().options()))
                        .execute();
                aiFactoryRepository.saveEntities(aiFactories.stream().map(aiFactory -> new AiFactoryCreateInput(aiFactory).toEntity())
                        .toList());
            }
        });
    }
}
