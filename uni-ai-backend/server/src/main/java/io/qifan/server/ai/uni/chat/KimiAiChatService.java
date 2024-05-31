package io.qifan.server.ai.uni.chat;

import io.qifan.ai.kimi.KimiAiChatModel;
import io.qifan.ai.kimi.KimiAiChatOptions;
import io.qifan.ai.kimi.KimiAiProperties;
import io.qifan.ai.kimi.api.KimiAiApi;
import io.qifan.infrastructure.common.exception.BusinessException;
import lombok.AllArgsConstructor;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.concurrent.Executor;

@Service
@AllArgsConstructor
public class KimiAiChatService implements UniAiChatService {
    private KimiAiProperties kimiAiProperties;
    private final Executor executor;

    @Override
    public ChatModel getChatModel(Map<String, Object> options) {
        String apiKey = (String) options.get("apiKey");
        if (!StringUtils.hasText(apiKey)) {
            apiKey = kimiAiProperties.getApiKey();
        }
        if (!StringUtils.hasText(apiKey)) {
            throw new BusinessException("apiKey不能为空");
        }
        return new KimiAiChatModel(new KimiAiApi(apiKey, executor));
    }

    @Override
    public ChatOptions getChatOptions(Map<String, Object> options) {
        KimiAiChatOptions kimiAiChatOptions = new KimiAiChatOptions();
        if (options.get("model") != null) {
            kimiAiChatOptions.setModel(String.valueOf(options.get("model")));
        }
        if (options.get("topP") != null) {
            kimiAiChatOptions.setTopP(Float.parseFloat(String.valueOf(options.get("topP"))));
        }
        if (options.get("topK") != null) {
            kimiAiChatOptions.setTopK(Integer.parseInt(String.valueOf(options.get("topK"))));
        }
        if (options.get("maxTokens") != null) {
            kimiAiChatOptions.setMaxTokens(Integer.parseInt(String.valueOf(options.get("maxTokens"))));
        }
        if (options.get("temperature") != null) {
            kimiAiChatOptions.setTemperature(Float.parseFloat(String.valueOf(options.get("temperature"))));
        }
        return kimiAiChatOptions;
    }
}
