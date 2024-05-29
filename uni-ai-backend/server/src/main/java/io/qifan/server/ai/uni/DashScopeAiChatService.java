package io.qifan.server.ai.uni;

import io.qifan.ai.dashscope.DashScopeAiChatModel;
import io.qifan.ai.dashscope.DashScopeAiChatOptions;
import lombok.AllArgsConstructor;
import org.springframework.ai.chat.model.StreamingChatModel;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@AllArgsConstructor
public class DashScopeAiChatService implements UniAiChatService {
    DashScopeAiChatModel chatModel;
    @Override
    public StreamingChatModel getChatModel() {
        return chatModel;
    }

    @Override
    public ChatOptions getChatOptions(Map<String, Object> options) {
        DashScopeAiChatOptions dashScopeAiChatOptions = new DashScopeAiChatOptions();
        if (options.containsKey("topP")) {
            dashScopeAiChatOptions.setTopP((Float) options.get("topP"));
        }
        if (options.containsKey("topK")) {
            dashScopeAiChatOptions.setTopK((Integer) options.get("topK"));
        }
        if (options.containsKey("model")) {
            dashScopeAiChatOptions.setModel((String) options.get("model"));
        }
        if (options.containsKey("maxTokens")) {
            dashScopeAiChatOptions.setMaxTokens((Integer) options.get("maxTokens"));
        }
        if (options.containsKey("temperature")) {
            dashScopeAiChatOptions.setTemperature((Float) options.get("temperature"));
        }
        return dashScopeAiChatOptions;
    }
}
