package io.qifan.server.ai.uni;

import io.qifan.ai.dashscope.DashScopeAiChatOptions;
import io.qifan.ai.dashscope.DashScopeAiVLChatModel;
import io.qifan.ai.dashscope.api.DashScopeAiApi;
import io.qifan.infrastructure.common.exception.BusinessException;
import org.springframework.ai.chat.model.StreamingChatModel;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;

@Service
public class DashScopeAiVLChatService implements UniAiChatService {

    @Override
    public StreamingChatModel getChatModel(Map<String, Object> options) {
        String apiKey = (String) options.get("apiKey");
        if (!StringUtils.hasText(apiKey)) {
            throw new BusinessException("apiKey不能为空");
        }
        return new DashScopeAiVLChatModel(new DashScopeAiApi(apiKey));
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
