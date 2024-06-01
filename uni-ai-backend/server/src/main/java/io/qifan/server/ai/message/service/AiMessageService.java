package io.qifan.server.ai.message.service;

import io.qifan.infrastructure.common.constants.ResultCode;
import io.qifan.infrastructure.common.exception.BusinessException;
import io.qifan.server.ai.message.entity.dto.AiMessageCreateInput;
import io.qifan.server.ai.message.entity.model.ChatMessage;
import io.qifan.server.ai.message.entity.model.ChatParams;
import io.qifan.server.ai.model.entity.AiModel;
import io.qifan.server.ai.session.entity.AiSession;
import io.qifan.server.ai.session.repository.AiSessionRepository;
import io.qifan.server.ai.tag.root.entity.AiTag;
import io.qifan.server.ai.uni.chat.UniAiChatService;
import io.qifan.server.ai.uni.vector.MilvusRepository;
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
public class AiMessageService {
    private final AiSessionRepository aiSessionRepository;
    private final Map<String, UniAiChatService> uniAiChatServiceMap;
    private final MilvusRepository milvusRepository;

    public Flux<ChatResponse> chat(AiMessageCreateInput messageInput, ChatParams params) {
        AiSession aiSession = aiSessionRepository.findById(messageInput.getAiSessionId(), AiSessionRepository.COMPLEX_FETCHER_FOR_FRONT)
                .orElseThrow(() -> new BusinessException(ResultCode.NotFindError, "会话不存在"));
        AiModel model = aiSession.aiModel();
        if (model == null) {
            throw new BusinessException("请配置模型");
        }
        if (model.tagsView() == null) {
            throw new BusinessException("请配置模型标签");
        }
        AiTag aiTag = model.tagsView().stream().filter(tag -> tag.name().equals(params.getTag()))
                .findFirst()
                .orElseThrow(() -> new BusinessException("模型不支持该场景"));
        UniAiChatService aiChatService = uniAiChatServiceMap.get(StringUtils.uncapitalize(aiTag.springAiModel()));
        if (aiChatService == null) {
            throw new BusinessException("后端未配置模型服务");
        }
        List<Message> messages = historyMessageList(aiSession);
        messages.add(toUserMessage(messageInput, params));
        Prompt prompt = new Prompt(messages, aiChatService.getChatOptions(model.options()));
        return aiChatService.getChatModel(model.options()).stream(prompt);
    }

    public Message toUserMessage(AiMessageCreateInput messageInput, ChatParams params) {
        ChatMessage chatMessage = toMessage(DictConstants.AiMessageType.USER, messageInput.getContent());
        if (params.getKnowledge()) {
            List<String> context = milvusRepository.similaritySearch(chatMessage.getContent(), params.getCollectionId())
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

    public List<Message> historyMessageList(AiSession aiSession) {
        List<Message> messages = new ArrayList<>(aiSession.messages()
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