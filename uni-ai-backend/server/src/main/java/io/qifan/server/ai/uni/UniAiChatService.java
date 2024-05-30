package io.qifan.server.ai.uni;

import org.springframework.ai.chat.model.StreamingChatModel;
import org.springframework.ai.chat.prompt.ChatOptions;

import java.util.Map;

public interface UniAiChatService {
    StreamingChatModel getChatModel(Map<String,Object> options);

    ChatOptions getChatOptions(Map<String, Object> options);
}
