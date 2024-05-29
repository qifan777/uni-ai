package io.qifan.server.ai.uni;

import io.qifan.ai.dashscope.DashScopeAiChatOptions;
import io.qifan.ai.dashscope.DashScopeAiVLChatModel;
import lombok.AllArgsConstructor;
import org.springframework.ai.chat.model.StreamingChatModel;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@AllArgsConstructor
public class DashScopeAiVLChatService implements UniAiChatService {
    private final DashScopeAiVLChatModel chatModel;

    @Override
    public StreamingChatModel getChatModel() {
        return chatModel;
    }

    @Override
    public ChatOptions getChatOptions(Map<String, Object> options) {
        DashScopeAiChatOptions dashScopeAiChatOptions = new DashScopeAiChatOptions();
        if (options.get("model") != null) {
            dashScopeAiChatOptions.setModel(String.valueOf(options.get("model")));
        }
        return dashScopeAiChatOptions;
    }
}
