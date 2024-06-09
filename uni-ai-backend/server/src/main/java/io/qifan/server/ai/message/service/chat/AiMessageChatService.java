package io.qifan.server.ai.message.service.chat;

import io.qifan.infrastructure.common.constants.ResultCode;
import io.qifan.infrastructure.common.exception.BusinessException;
import io.qifan.server.ai.factory.entity.AiFactory;
import io.qifan.server.ai.factory.repository.AiFactoryRepository;
import io.qifan.server.ai.message.entity.dto.AiMessageCreateInput;
import io.qifan.server.ai.message.entity.dto.ChatMessageRequest;
import io.qifan.server.ai.message.entity.model.ChatMessage;
import io.qifan.server.ai.message.entity.model.ChatParams;
import io.qifan.server.ai.model.entity.AiModel;
import io.qifan.server.ai.model.entity.AiModelFetcher;
import io.qifan.server.ai.model.repository.AiModelRepository;
import io.qifan.server.ai.role.repository.AiRoleRepository;
import io.qifan.server.ai.session.entity.AiSession;
import io.qifan.server.ai.session.repository.AiSessionRepository;
import io.qifan.server.ai.tag.root.entity.AiTag;
import io.qifan.server.ai.tag.root.entity.AiTagFetcher;
import io.qifan.server.ai.uni.chat.UniAiChatService;
import io.qifan.server.ai.uni.vector.UniAiVectorService;
import io.qifan.server.dict.model.DictConstants;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.messages.Media;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.MessageType;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.MimeTypeUtils;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class AiMessageChatService {
    private final AiSessionRepository aiSessionRepository;
    private final Map<String, UniAiChatService> uniAiChatServiceMap;
    private final UniAiVectorService uniAiVectorService;
    private final AiModelRepository aiModelRepository;
    private final AiRoleRepository aiRoleRepository;
    private final AiFactoryRepository aiFactoryRepository;

    public Flux<ChatResponse> chat(ChatMessageRequest request) {
        AiSession aiSession = aiSessionRepository.findById(request.getMessage().getAiSessionId(), AiSessionRepository.COMPLEX_FETCHER_FOR_FRONT)
                .orElseThrow(() -> new BusinessException(ResultCode.NotFindError, "会话不存在"));
        AiModel aiModel = aiModelRepository.findById(request.getChatParams().getAiModelId(), AiModelFetcher.$.allScalarFields()
                        .tagsView(AiTagFetcher.$.allScalarFields()))
                .orElseThrow(() -> new BusinessException(ResultCode.NotFindError, "模型不存在"));
        if (aiModel == null) {
            throw new BusinessException("清选择模式");
        }
        if (aiModel.tagsView() == null) {
            throw new BusinessException("请配置模型标签");
        }
        AiFactory aiFactory = aiFactoryRepository.findUserFactory(aiModel.factory());
        AiTag aiTag = aiModel.tagsView().stream().filter(tag -> tag.name().equals(request.getChatParams().getTag()))
                .findFirst()
                .orElseThrow(() -> new BusinessException("模型不支持该场景"));
        UniAiChatService aiChatService = uniAiChatServiceMap.get(StringUtils.uncapitalize(aiTag.service()));
        if (aiChatService == null) {
            throw new BusinessException("后端未配置模型服务");
        }
        List<Message> messages = historyMessageList(aiSession, request.getChatParams().getAiRoleId());
        messages.add(toUserMessage(request.getMessage(), request.getChatParams()));
        Prompt prompt = new Prompt(messages);
        Map<String, Object> options = aiFactory.options();
        options.putAll(aiModel.options());
        if (request.getChatParams().getOptions() != null) {
            options.putAll(request.getChatParams().getOptions());
        }
        if (!CollectionUtils.isEmpty(request.getChatParams().getPluginNames())) {
            options.put("functions", request.getChatParams().getPluginNames());
        }
        return aiChatService.getChatModel(options).stream(prompt);
    }

    public Message toUserMessage(AiMessageCreateInput messageInput, ChatParams params) {
        ChatMessage chatMessage = toMessage(DictConstants.AiMessageType.USER, messageInput.getContent());
        if (StringUtils.hasText(params.getAiCollectionId())) {
            List<String> context = uniAiVectorService.similaritySearch(chatMessage.getContent(), params.getAiCollectionId())
                    .stream()
                    .map(Document::getContent)
                    .toList();
            log.info("context:{}", context);
            return new PromptTemplate("""
                    请你根据以下内容：{context}回答用户的提问。
                    如果提供的内容无法回答用户提问，请用自己的知识回答用户的提问，并告知用户知识库数据不足。
                    用户提问：{query}。
                    """)
                    .createMessage(Map.of("context", String.join("\n", context), "query", chatMessage.getContent()));
        }
        return chatMessage;
    }

    public List<Message> historyMessageList(AiSession aiSession, String aiRoleId) {
        List<Message> messages = new ArrayList<>(aiSession.messages()
                .stream()
                .map(message -> toMessage(message.type(), message.content())
                )
                .toList());
        aiRoleRepository.findById(aiRoleId).ifPresent(aiRole -> {
            messages.addAll(0, aiRole
                    .prompts()
                    .stream()
                    .map(message -> toMessage(message.getType(), message.getContent()))
                    .toList());
        });
        return messages;
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