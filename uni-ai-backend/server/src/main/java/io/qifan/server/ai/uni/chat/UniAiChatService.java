package io.qifan.server.ai.uni.chat;

import org.springframework.ai.chat.model.ChatModel;

import java.util.Map;

public interface UniAiChatService {
    ChatModel getChatModel(Map<String, Object> options);
}
