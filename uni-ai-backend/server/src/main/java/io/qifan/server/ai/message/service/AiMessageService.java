package io.qifan.server.ai.message.service;

import cn.dev33.satoken.stp.StpUtil;
import io.qifan.infrastructure.common.constants.ResultCode;
import io.qifan.infrastructure.common.exception.BusinessException;
import io.qifan.server.ai.message.entity.dto.AiMessageCreateInput;
import io.qifan.server.ai.message.entity.model.ChatMessage;
import io.qifan.server.ai.model.entity.AiModel;
import io.qifan.server.ai.session.entity.AiSession;
import io.qifan.server.ai.session.repository.AiSessionRepository;
import io.qifan.server.ai.tag.root.entity.AiTag;
import io.qifan.server.ai.uni.UniAiChatService;
import io.qifan.server.dict.model.DictConstants;
import io.qifan.server.setting.SettingRepository;
import io.qifan.server.wallet.record.entity.dto.WalletRecordCreateInput;
import io.qifan.server.wallet.record.service.WalletRecordService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.messages.AbstractMessage;
import org.springframework.ai.chat.messages.Media;
import org.springframework.ai.chat.messages.MessageType;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MimeTypeUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import reactor.core.publisher.Flux;

import java.math.BigDecimal;
import java.math.MathContext;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class AiMessageService {
    private final AiSessionRepository aiSessionRepository;
    private final Map<String, UniAiChatService> uniAiChatServiceMap;
    private final AsyncTaskExecutor executor;
    private final WalletRecordService walletRecordService;
    private final SettingRepository settingRepository;

    public Flux<ChatResponse> chat(AiMessageCreateInput messageInput, DictConstants.AiModelTag modelTag) {
        AiSession aiSession = aiSessionRepository.findById(messageInput.getAiSessionId(), AiSessionRepository.COMPLEX_FETCHER_FOR_FRONT)
                .orElseThrow(() -> new BusinessException(ResultCode.NotFindError, "会话不存在"));
        AiModel model = aiSession.aiModel();
        if (model == null) {
            throw new BusinessException("请配置模型");
        }
        if (model.tagsView() == null) {
            throw new BusinessException("请配置模型标签");
        }
        AiTag aiTag = model.tagsView().stream().filter(tag -> tag.name().equals(modelTag))
                .findFirst()
                .orElseThrow(() -> new BusinessException("模型不支持该场景"));

        UniAiChatService aiChatService = uniAiChatServiceMap.get(StringUtils.uncapitalize(aiTag.springAiModel()));
        if (aiChatService == null) {
            throw new BusinessException("后端未配置模型服务");
        }
        Prompt prompt = toPrompt(messageInput, aiSession, aiChatService.getChatOptions(model.options()));
        Flux<ChatResponse> stream = aiChatService.getChatModel(model.options()).stream(prompt);
        executor.submit(() -> {
            RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(new MockHttpServletRequest()));
            StpUtil.switchTo(aiSession.creator().id());
            Long totalTokens = stream.map(chatResponse -> chatResponse.getMetadata().getUsage().getTotalTokens())
                    .reduce(0L, Long::sum)
                    .block();
            BigDecimal tokenPrice = settingRepository.get().getTokenPrice();
            BigDecimal price = BigDecimal.valueOf(totalTokens).divide(BigDecimal.valueOf(1000), MathContext.DECIMAL64)
                    .multiply(tokenPrice);
            log.info("totalTokens:{},price:{}", totalTokens, price);
            log.info("消费记录创建");
            walletRecordService.create(new WalletRecordCreateInput.Builder()
                    .walletId(aiSession.creator().id())
                    .amount(BigDecimal.ZERO.subtract(price))
                    .description(totalTokens.toString())
                    .type(DictConstants.WalletRecordType.CHAT)
                    .build());

        });
        return stream;
    }


    private Prompt toPrompt(AiMessageCreateInput messageInput, AiSession aiSession, ChatOptions options) {
        List<AbstractMessage> messages = new ArrayList<>(aiSession.messages()
                .stream()
                .map(message -> toMessage(message.type(), message.content())
                )
                .toList());
        if (aiSession.aiRole() != null) {
            messages.addAll(0, aiSession.aiRole()
                    .prompts()
                    .stream()
                    .map(message -> toMessage(message.getType(), message.getContent()))
                    .toList());
        }
        messages.add(toMessage(messageInput.getType(), messageInput.getContent()));
        return new Prompt(new ArrayList<>(messages), options);
    }

    public ChatMessage toMessage(DictConstants.AiMessageType type, List<Map<String, Object>> content) {
        String textContent = content
                .stream()
                .filter(item -> StringUtils.hasText((String) item.get("text")))
                .findFirst()
                .map(item -> (String) item.get("text"))
                .orElse("");
        List<Media> mediaList = content
                .stream()
                .filter(item -> StringUtils.hasText((String) item.get("image")))
                .map(item -> {
                    try {
                        return new Media(MimeTypeUtils.IMAGE_PNG, new URL((String) item.get("image")));
                    } catch (MalformedURLException e) {
                        throw new RuntimeException(e);
                    }
                })
                .toList();
        return new ChatMessage(MessageType.valueOf(type.toString()), textContent, mediaList);
    }

}