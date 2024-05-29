package io.qifan.server.ai.message.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import io.qifan.infrastructure.common.exception.BusinessException;
import io.qifan.server.ai.message.entity.AiMessage;
import io.qifan.server.ai.message.entity.dto.AiMessageCreateInput;
import io.qifan.server.ai.message.entity.dto.AiMessageSpec;
import io.qifan.server.ai.message.entity.dto.AiMessageUpdateInput;
import io.qifan.server.ai.message.repository.AiMessageRepository;
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
@RequestMapping("admin/ai-message")
@AllArgsConstructor
@DefaultFetcherOwner(AiMessageRepository.class)
@SaCheckPermission("/ai-message")
@Transactional
public class AiMessageForAdminController {
    private final AiMessageRepository aiMessageRepository;

    @GetMapping("{id}")
    public @FetchBy(value = "COMPLEX_FETCHER_FOR_ADMIN") AiMessage findById(@PathVariable String id) {
        return aiMessageRepository.findById(id, AiMessageRepository.COMPLEX_FETCHER_FOR_ADMIN).orElseThrow(() -> new BusinessException("数据不存在"));
    }

    @PostMapping("query")
    public Page<@FetchBy(value = "COMPLEX_FETCHER_FOR_ADMIN") AiMessage> query(@RequestBody QueryRequest<AiMessageSpec> queryRequest) {
        return aiMessageRepository.findPage(queryRequest, AiMessageRepository.COMPLEX_FETCHER_FOR_ADMIN);
    }

    @PostMapping
    public String create(@RequestBody @Validated AiMessageCreateInput aiMessageInput) {
        return aiMessageRepository.save(aiMessageInput).id();
    }

    @PutMapping
    public String update(@RequestBody @Validated AiMessageUpdateInput aiMessageInput) {
        return aiMessageRepository.save(aiMessageInput).id();
    }

    @DeleteMapping
    public Boolean delete(@RequestBody List<String> ids) {
        aiMessageRepository.deleteAllById(ids);
        return true;
    }
}