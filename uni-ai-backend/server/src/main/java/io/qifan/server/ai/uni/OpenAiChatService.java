package io.qifan.server.ai.uni;

import lombok.AllArgsConstructor;
import org.springframework.ai.chat.model.StreamingChatModel;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@AllArgsConstructor
public class OpenAiChatService implements UniAiChatService {
    OpenAiChatModel openAiChatModel;

    @Override
    public StreamingChatModel getChatModel() {
        return openAiChatModel;
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
