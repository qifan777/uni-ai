package io.qifan.server.ai.uni.chat;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.qifan.ai.spark.SparkAiChatModel;
import io.qifan.ai.spark.SparkAiChatOptions;
import io.qifan.ai.spark.SparkAiProperties;
import io.qifan.ai.spark.api.SparkAiApi;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.concurrent.Executor;

@Service
@AllArgsConstructor
public class SparkAiChatService implements UniAiChatService {
    private final Executor executor;
    private final ObjectMapper objectMapper;
    private final SparkAiProperties sparkAiProperties;

    @SneakyThrows
    @Override
    public ChatModel getChatModel(Map<String, Object> options) {
        String apiKey = (String) options.get("apiKey");
        String apiSecret = (String) options.get("apiSecret");
        String appId = (String) options.get("appId");
        if (!StringUtils.hasText(apiKey) || !StringUtils.hasText(apiSecret) || !StringUtils.hasText(appId)) {
            apiKey = sparkAiProperties.getApiKey();
            apiSecret = sparkAiProperties.getApiSecret();
            appId = sparkAiProperties.getAppId();
        }
        if (!StringUtils.hasText(apiKey) || !StringUtils.hasText(apiSecret) || !StringUtils.hasText(appId)) {
            throw new RuntimeException("apiKey, apiSecret, appId不能为空");
        }
        String valueAsString = objectMapper.writeValueAsString(options);
        SparkAiChatOptions chatOptions = objectMapper.readValue(valueAsString, SparkAiChatOptions.class);
        return new SparkAiChatModel(new SparkAiApi(apiKey, apiSecret, appId, objectMapper, executor), chatOptions);
    }
}
