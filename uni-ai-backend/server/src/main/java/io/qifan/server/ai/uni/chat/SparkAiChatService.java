package io.qifan.server.ai.uni.chat;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.qifan.ai.spark.SparkAiChatModel;
import io.qifan.ai.spark.SparkAiChatOptions;
import io.qifan.ai.spark.SparkAiProperties;
import io.qifan.ai.spark.api.SparkAiApi;
import lombok.AllArgsConstructor;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.ChatOptions;
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
        return new SparkAiChatModel(new SparkAiApi(apiKey, apiSecret, appId, objectMapper, executor));
    }

    @Override
    public ChatOptions getChatOptions(Map<String, Object> options) {
        SparkAiChatOptions sparkAiChatOptions = new SparkAiChatOptions();
        if (options.get("domain") != null) {
            sparkAiChatOptions.setDomain(String.valueOf(options.get("domain")));
        }
        if (options.containsKey("baseUrl")) {
            sparkAiChatOptions.setBaseUrl(String.valueOf(options.get("baseUrl")));
        }
        if (options.get("maxTokens") != null) {
            sparkAiChatOptions.setMaxTokens(Integer.parseInt(String.valueOf(options.get("maxTokens"))));
        }
        if (options.get("temperature") != null) {
            sparkAiChatOptions.setTemperature(Float.parseFloat(String.valueOf(options.get("temperature"))));
        }
        if (options.get("topK") != null) {
            sparkAiChatOptions.setTopK(Integer.parseInt(String.valueOf(options.get("topK"))));
        }
        if (options.get("topP") != null) {
            sparkAiChatOptions.setTopP(Float.parseFloat(String.valueOf(options.get("topP"))));
        }
        return sparkAiChatOptions;
    }
}
