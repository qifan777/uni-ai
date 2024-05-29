package io.qifan.server.ai.uni;

import io.qifan.ai.qianfan.QianFanAiChatModel;
import io.qifan.ai.qianfan.QianFanAiChatOptions;
import lombok.AllArgsConstructor;
import org.springframework.ai.chat.model.StreamingChatModel;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@AllArgsConstructor
public class QianFanAiChatService implements UniAiChatService {
    private final QianFanAiChatModel qianFanAiChatModel;

    @Override
    public StreamingChatModel getChatModel() {
        return qianFanAiChatModel;
    }

    @Override
    public ChatOptions getChatOptions(Map<String, Object> options) {
        QianFanAiChatOptions qianFanAiChatOptions = new QianFanAiChatOptions();
        if (options.containsKey("model")) {
            qianFanAiChatOptions.setModel((String) options.get("model"));
        }
        if (options.containsKey("topK")) {
            qianFanAiChatOptions.setTopK(Integer.valueOf(options.get("topK").toString()));
        }
        if (options.containsKey("maxTokens")) {
            qianFanAiChatOptions.setMaxTokens(Integer.valueOf(options.get("maxTokens").toString()));
        }
        if (options.containsKey("temperature")) {
            qianFanAiChatOptions.setTemperature(Float.valueOf(options.get("temperature").toString()));
        }
        return qianFanAiChatOptions;
    }
}
