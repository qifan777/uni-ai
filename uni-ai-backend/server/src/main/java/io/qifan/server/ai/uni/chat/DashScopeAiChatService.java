package io.qifan.server.ai.uni.chat;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.qifan.ai.dashscope.DashScopeAiChatModel;
import io.qifan.ai.dashscope.DashScopeAiChatOptions;
import io.qifan.ai.dashscope.DashScopeAiProperties;
import io.qifan.ai.dashscope.api.DashScopeAiApi;
import io.qifan.infrastructure.common.exception.BusinessException;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.model.function.FunctionCallbackContext;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
@AllArgsConstructor
public class DashScopeAiChatService implements UniAiChatService {
    private final DashScopeAiProperties dashScopeAiProperties;
    private final ObjectMapper objectMapper;
    private final FunctionCallbackContext functionCallbackContext;

    @SneakyThrows
    @Override
    public ChatModel getChatModel(Map<String, Object> options) {
        String apiKey = (String) options.get("apiKey");
        if (!StringUtils.hasText(apiKey)) {
            apiKey = dashScopeAiProperties.getApiKey();
        }
        if (!StringUtils.hasText(apiKey)) {
            throw new BusinessException("apiKey不能为空");
        }
        String valueAsString = objectMapper.writeValueAsString(options);
        DashScopeAiChatOptions chatOptions = objectMapper.readValue(valueAsString, DashScopeAiChatOptions.class);
        if (options.containsKey("functions")&& options.get("functions") instanceof List functions){
            chatOptions.setFunctions(new HashSet<>(functions));
        }
        return new DashScopeAiChatModel(functionCallbackContext, new DashScopeAiApi(apiKey), chatOptions);
    }
}
