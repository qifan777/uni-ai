package io.qifan.server.ai.uni.chat;

import com.alibaba.cloud.ai.autoconfigure.dashscope.DashScopeChatProperties;
import com.alibaba.cloud.ai.dashscope.api.DashScopeApi;
import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatModel;
import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatOptions;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.qifan.infrastructure.common.exception.BusinessException;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.model.function.FunctionCallbackContext;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class DashScopeAiChatService implements UniAiChatService {
    private final DashScopeChatProperties dashScopeChatProperties;
    private final ObjectMapper objectMapper;
    private final FunctionCallbackContext functionCallbackContext;

    @SneakyThrows
    @Override
    public ChatModel getChatModel(Map<String, Object> options) {
        String apiKey = (String) options.get("apiKey");
        if (!StringUtils.hasText(apiKey)) {
            apiKey = dashScopeChatProperties.getApiKey();
        }
        if (!StringUtils.hasText(apiKey)) {
            throw new BusinessException("apiKey不能为空");
        }
        String valueAsString = objectMapper.writeValueAsString(options);
        DashScopeChatOptions chatOptions = objectMapper.readValue(valueAsString, DashScopeChatOptions.class);
        if (options.containsKey("functions") && options.get("functions") instanceof List functions) {
            chatOptions.setFunctions(new HashSet<>(functions));
        }
        return new DashScopeChatModel(new DashScopeApi(apiKey), chatOptions, functionCallbackContext, RetryTemplate.defaultInstance());
    }
}
