package io.qifan.server.ai.uni;

import io.qifan.infrastructure.common.exception.BusinessException;
import org.springframework.ai.chat.model.StreamingChatModel;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;

@Service
public class OpenAiChatService implements UniAiChatService {

    @Override
    public StreamingChatModel getChatModel(Map<String, Object> options) {
        String apiKey = (String) options.get("apiKey");
        if (!StringUtils.hasText(apiKey)) {
            throw new BusinessException("apiKey不能为空");
        }
        return new OpenAiChatModel(new OpenAiApi(apiKey));
    }

    @Override
    public ChatOptions getChatOptions(Map<String, Object> options) {
        OpenAiChatOptions openAiChatOptions = new OpenAiChatOptions();
        if (options.get("model") != null) {
            openAiChatOptions.setModel(String.valueOf(options.get("model")));
        }
        if (options.get("topP") != null) {
            openAiChatOptions.setTopP(Float.parseFloat(String.valueOf(options.get("topP"))));
        }
        if (options.get("topK") != null) {
            openAiChatOptions.setTopK(Integer.parseInt(String.valueOf(options.get("topK"))));
        }
        if (options.get("presencePenalty") != null) {
            openAiChatOptions.setPresencePenalty(Float.parseFloat(String.valueOf(options.get("presencePenalty"))));
        }
        if (options.get("frequencyPenalty") != null) {
            openAiChatOptions.setFrequencyPenalty(Float.parseFloat(String.valueOf(options.get("frequencyPenalty"))));
        }
        if (options.get("maxTokens") != null) {
            openAiChatOptions.setMaxTokens(Integer.parseInt(String.valueOf(options.get("maxTokens"))));
        }
        if (options.get("temperature") != null) {
            openAiChatOptions.setTemperature(Float.parseFloat(String.valueOf(options.get("temperature"))));
        }
        return openAiChatOptions;
    }
}
