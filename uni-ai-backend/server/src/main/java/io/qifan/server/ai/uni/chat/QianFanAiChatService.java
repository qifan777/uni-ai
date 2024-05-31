package io.qifan.server.ai.uni.chat;

import io.qifan.ai.qianfan.QianFanAiChatModel;
import io.qifan.ai.qianfan.QianFanAiChatOptions;
import io.qifan.ai.qianfan.QianFanAiProperties;
import io.qifan.ai.qianfan.api.QianFanApi;
import io.qifan.infrastructure.common.exception.BusinessException;
import lombok.AllArgsConstructor;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;

@Service
@AllArgsConstructor
public class QianFanAiChatService implements UniAiChatService {
    private final QianFanAiProperties qianFanAiProperties;

    @Override
    public ChatModel getChatModel(Map<String, Object> options) {
        String accessKey = (String) options.get("accessKey");
        String secretKey = (String) options.get("secretKey");
        if (!StringUtils.hasText(accessKey) || !StringUtils.hasText(secretKey)) {
            accessKey = qianFanAiProperties.getAccessKey();
            secretKey = qianFanAiProperties.getSecretKey();
        }
        if (!StringUtils.hasText(accessKey) || !StringUtils.hasText(secretKey)) {
            throw new BusinessException("accessKey or secretKey 不能为空");
        }
        return new QianFanAiChatModel(new QianFanApi(accessKey, secretKey));
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
