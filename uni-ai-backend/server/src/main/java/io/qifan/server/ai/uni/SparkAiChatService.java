package io.qifan.server.ai.uni;

import io.qifan.ai.spark.SparkAiChatModel;
import io.qifan.ai.spark.SparkAiChatOptions;
import lombok.AllArgsConstructor;
import org.springframework.ai.chat.model.StreamingChatModel;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@AllArgsConstructor
public class SparkAiChatService implements UniAiChatService {
    private final SparkAiChatModel sparkAiChatModel;

    @Override
    public StreamingChatModel getChatModel() {
        return sparkAiChatModel;
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
