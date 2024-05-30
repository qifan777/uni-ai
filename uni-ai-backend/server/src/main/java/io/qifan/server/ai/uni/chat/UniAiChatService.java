package io.qifan.server.ai.uni.chat;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.ChatOptions;

import java.util.Map;

public interface UniAiChatService {
    ChatModel getChatModel(Map<String, Object> options);

    ChatOptions getChatOptions(Map<String, Object> options);
}
