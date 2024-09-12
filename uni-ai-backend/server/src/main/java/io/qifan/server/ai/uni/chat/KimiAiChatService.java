package io.qifan.server.ai.uni.chat;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.qifan.infrastructure.common.exception.BusinessException;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.ai.autoconfigure.moonshot.MoonshotChatProperties;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.model.function.FunctionCallbackContext;
import org.springframework.ai.moonshot.MoonshotChatModel;
import org.springframework.ai.moonshot.MoonshotChatOptions;
import org.springframework.ai.moonshot.api.MoonshotApi;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestClient;

import java.util.Map;

@Service
@AllArgsConstructor
public class KimiAiChatService implements UniAiChatService {
    private final ObjectMapper objectMapper;
    private final ResponseErrorHandler responseErrorHandler;
    private MoonshotChatProperties moonshotChatProperties;
    private final FunctionCallbackContext functionCallbackContext;

    @SneakyThrows
    @Override
    public ChatModel getChatModel(Map<String, Object> options) {
        String apiKey = (String) options.get("apiKey");
        if (!StringUtils.hasText(apiKey)) {
            apiKey = moonshotChatProperties.getApiKey();
        }
        if (!StringUtils.hasText(apiKey)) {
            throw new BusinessException("apiKey不能为空");
        }
        String valueAsString = objectMapper.writeValueAsString(options);
        MoonshotChatOptions chatOptions = objectMapper.readValue(valueAsString, MoonshotChatOptions.class);
        MoonshotApi api = new MoonshotApi("https://api.moonshot.cn", apiKey, RestClient.builder(), responseErrorHandler);
        return new MoonshotChatModel(api, chatOptions, functionCallbackContext, RetryTemplate.defaultInstance());
    }
}
