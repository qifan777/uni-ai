package io.qifan.server.ai.uni;

import io.qifan.ai.dashscope.DashScopeAiChatModel;
import io.qifan.ai.dashscope.DashScopeAiChatOptions;
import io.qifan.ai.dashscope.api.DashScopeAiApi;
import io.qifan.infrastructure.common.exception.BusinessException;
import org.springframework.ai.chat.model.StreamingChatModel;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;

@Service
public class DashScopeAiChatService implements UniAiChatService {
    @Override
    public StreamingChatModel getChatModel(Map<String, Object> options) {
        String apiKey = (String) options.get("apiKey");
        if (!StringUtils.hasText(apiKey)) {
            throw new BusinessException("apiKey不能为空");
        }
        return new DashScopeAiChatModel(new DashScopeAiApi(apiKey));
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
