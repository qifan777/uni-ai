
package io.qifan.server.ai.message.controller;

import cn.dev33.satoken.annotation.SaCheckDisable;
import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.qifan.infrastructure.common.exception.BusinessException;
import io.qifan.server.ai.message.entity.AiMessage;
import io.qifan.server.ai.message.entity.dto.AiMessageCreateInput;
import io.qifan.server.ai.message.entity.dto.AiMessageSpec;
import io.qifan.server.ai.message.entity.dto.AiMessageUpdateInput;
import io.qifan.server.ai.message.repository.AiMessageRepository;
import io.qifan.server.ai.message.service.AiMessageService;
import io.qifan.server.dict.model.DictConstants;
import io.qifan.server.infrastructure.model.QueryRequest;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.babyfish.jimmer.client.ApiIgnore;
import org.babyfish.jimmer.client.FetchBy;
import org.babyfish.jimmer.client.meta.DefaultFetcherOwner;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.List;

@RestController
@RequestMapping("front/ai-message")
@AllArgsConstructor
@DefaultFetcherOwner(AiMessageRepository.class)
@Transactional
@SaCheckLogin
@SaCheckDisable
public class AiMessageForFrontController {
    private final AiMessageRepository aiMessageRepository;
    private final AiMessageService aiMessageService;
    private final ObjectMapper jacksonObjectMapper;

    @GetMapping("{id}")
    public @FetchBy(value = "COMPLEX_FETCHER_FOR_FRONT") AiMessage findById(@PathVariable String id) {
        return aiMessageRepository.findById(id, AiMessageRepository.COMPLEX_FETCHER_FOR_FRONT).orElseThrow(() -> new BusinessException("数据不存在"));
    }

    @PostMapping("query")
    public Page<@FetchBy(value = "COMPLEX_FETCHER_FOR_FRONT") AiMessage> query(@RequestBody QueryRequest<AiMessageSpec> queryRequest) {
        queryRequest.getQuery().setCreatorId(StpUtil.getLoginIdAsString());
        return aiMessageRepository.findPage(queryRequest, AiMessageRepository.COMPLEX_FETCHER_FOR_FRONT);
    }

    @PostMapping
    public String create(@RequestBody @Validated AiMessageCreateInput aiMessageCreateInput) {
        return aiMessageRepository.save(aiMessageCreateInput).id();
    }

    @PutMapping
    public String update(@RequestBody @Validated AiMessageUpdateInput aiMessageUpdateInput) {
        AiMessage aiMessage = aiMessageRepository.findById(aiMessageUpdateInput.getId(), AiMessageRepository.COMPLEX_FETCHER_FOR_FRONT).orElseThrow(() -> new BusinessException("数据不存在"));
        if (!aiMessage.creator().id().equals(StpUtil.getLoginIdAsString())) {
            throw new BusinessException("只能修改自己的数据");
        }
        return aiMessageRepository.save(aiMessageUpdateInput).id();
    }

    @DeleteMapping
    public Boolean delete(@RequestBody List<String> ids) {
        aiMessageRepository.findByIds(ids, AiMessageRepository.COMPLEX_FETCHER_FOR_FRONT).forEach(aiMessage -> {
            if (!aiMessage.creator().id().equals(StpUtil.getLoginIdAsString())) {
                throw new BusinessException("只能删除自己的数据");
            }
        });
        aiMessageRepository.deleteAllById(ids);
        return true;
    }

    @PostMapping(value = "chat", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @ApiIgnore
    public Flux<ServerSentEvent<String>> chat(@RequestBody AiMessageCreateInput messageInput, @RequestParam DictConstants.AiModelTag tag) {
        return aiMessageService
                .chat(messageInput,tag)
                .map(this::apply);
    }

    @SneakyThrows
    private ServerSentEvent<String> apply(ChatResponse chatResponse) {
        return ServerSentEvent.<String>builder()
                .data(jacksonObjectMapper.writeValueAsString(chatResponse))
                .event("chat")
                .build();
    }
}
