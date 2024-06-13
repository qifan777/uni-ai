package io.qifan.server.ai.uni.chat;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.qifan.ai.kimi.KimiAiChatModel;
import io.qifan.ai.kimi.KimiAiChatOptions;
import io.qifan.ai.kimi.KimiAiProperties;
import io.qifan.ai.kimi.api.KimiAiApi;
import io.qifan.infrastructure.common.exception.BusinessException;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.model.function.FunctionCallbackContext;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClient;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Service
@AllArgsConstructor
public class KimiAiChatService implements UniAiChatService {
    private KimiAiProperties kimiAiProperties;
    private final ObjectMapper objectMapper;
    private final FunctionCallbackContext functionCallbackContext;

    @SneakyThrows
    @Override
    public ChatModel getChatModel(Map<String, Object> options) {
        String apiKey = (String) options.get("apiKey");
        if (!StringUtils.hasText(apiKey)) {
            apiKey = kimiAiProperties.getApiKey();
        }
        if (!StringUtils.hasText(apiKey)) {
            throw new BusinessException("apiKey不能为空");
        }
        String valueAsString = objectMapper.writeValueAsString(options);
        KimiAiChatOptions chatOptions = objectMapper.readValue(valueAsString, KimiAiChatOptions.class);
        KimiAiApi api = new KimiAiApi(apiKey, "https://api.moonshot.cn", RestClient.builder(), WebClient.builder());
        return new KimiAiChatModel(functionCallbackContext, api, chatOptions, RetryTemplate.defaultInstance());
    }

}
