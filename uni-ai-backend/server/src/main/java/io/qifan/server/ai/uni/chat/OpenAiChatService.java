package io.qifan.server.ai.uni.chat;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.qifan.infrastructure.common.exception.BusinessException;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.ai.autoconfigure.openai.OpenAiConnectionProperties;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;

@Service
@AllArgsConstructor
public class OpenAiChatService implements UniAiChatService {
    private final ObjectMapper objectMapper;
    private OpenAiConnectionProperties openAiConnectionProperties;

    @SneakyThrows
    @Override
    public ChatModel getChatModel(Map<String, Object> options) {
        String apiKey = (String) options.get("apiKey");
        String baseUrl = (String) options.get("baseUrl");
        if (!StringUtils.hasText(apiKey)) {
            apiKey = openAiConnectionProperties.getApiKey();
        }
        if (!StringUtils.hasText(baseUrl)) {
            baseUrl = openAiConnectionProperties.getBaseUrl();
        }
        if (!StringUtils.hasText(apiKey)) {
            throw new BusinessException("apiKey不能为空");
        }
        if (!StringUtils.hasText(baseUrl)) {
            throw new BusinessException("baseUrl不能为空");
        }
        String valueAsString = objectMapper.writeValueAsString(options);
        OpenAiChatOptions chatOptions = objectMapper.readValue(valueAsString, OpenAiChatOptions.class);
        return new OpenAiChatModel(new OpenAiApi(baseUrl, apiKey), chatOptions);
    }
}
