package io.qifan.server.ai.uni.chat;

import io.qifan.infrastructure.common.exception.BusinessException;
import lombok.AllArgsConstructor;
import org.springframework.ai.autoconfigure.zhipuai.ZhiPuAiConnectionProperties;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.zhipuai.ZhiPuAiChatModel;
import org.springframework.ai.zhipuai.ZhiPuAiChatOptions;
import org.springframework.ai.zhipuai.api.ZhiPuAiApi;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;

@Service
@AllArgsConstructor
public class ZhiPuAiChatService implements UniAiChatService {
    private final ZhiPuAiConnectionProperties zhiPuAiConnectionProperties;

    @Override
    public ChatModel getChatModel(Map<String, Object> options) {
        String apiKey = (String) options.get("apiKey");
        if (!StringUtils.hasText(apiKey)) {
            apiKey = zhiPuAiConnectionProperties.getApiKey();
        }
        if (!StringUtils.hasText(apiKey)) {
            throw new BusinessException("apiKey不能为空");
        }
        return new ZhiPuAiChatModel(new ZhiPuAiApi(apiKey));
    }

    @Override
    public ChatOptions getChatOptions(Map<String, Object> options) {
        ZhiPuAiChatOptions zhiPuAiChatOptions = new ZhiPuAiChatOptions();
        if (options.get("model") != null) {
            zhiPuAiChatOptions.setModel(String.valueOf(options.get("model")));
        }
        if (options.get("topP") != null) {
            zhiPuAiChatOptions.setTopP(Float.parseFloat(String.valueOf(options.get("topP"))));
        }
        if (options.get("topK") != null) {
            zhiPuAiChatOptions.setTopK(Integer.parseInt(String.valueOf(options.get("topK"))));
        }
        if (options.get("presencePenalty") != null) {
            zhiPuAiChatOptions.setPresencePenalty(Float.parseFloat(String.valueOf(options.get("presencePenalty"))));
        }
        if (options.get("frequencyPenalty") != null) {
            zhiPuAiChatOptions.setFrequencyPenalty(Float.parseFloat(String.valueOf(options.get("frequencyPenalty"))));
        }
        if (options.get("maxTokens") != null) {
            zhiPuAiChatOptions.setMaxTokens(Integer.parseInt(String.valueOf(options.get("maxTokens"))));
        }
        if (options.get("temperature") != null) {
            zhiPuAiChatOptions.setTemperature(Float.parseFloat(String.valueOf(options.get("temperature"))));
        }
        return zhiPuAiChatOptions;
    }
}
