package io.qifan.server.ai.uni.chat;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.qifan.infrastructure.common.exception.BusinessException;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.ai.autoconfigure.qianfan.QianFanChatProperties;
import org.springframework.ai.autoconfigure.qianfan.QianFanConnectionProperties;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.model.function.FunctionCallbackContext;
import org.springframework.ai.qianfan.QianFanChatModel;
import org.springframework.ai.qianfan.QianFanChatOptions;
import org.springframework.ai.qianfan.api.QianFanApi;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;

@Service
@AllArgsConstructor
public class QianFanAiChatService implements UniAiChatService {
    private final QianFanConnectionProperties qianFanConnectionProperties;
    private final QianFanChatProperties qianFanChatProperties;
    private final ObjectMapper objectMapper;
    private final FunctionCallbackContext functionCallbackContext;

    @SneakyThrows
    @Override
    public ChatModel getChatModel(Map<String, Object> options) {
        String apiKey = (String) options.get("apiKey");
        String secretKey = (String) options.get("secretKey");
        if (!StringUtils.hasText(apiKey) || !StringUtils.hasText(secretKey)) {
            apiKey = qianFanConnectionProperties.getApiKey();
            secretKey = qianFanConnectionProperties.getSecretKey();
        }
        if (!StringUtils.hasText(apiKey) || !StringUtils.hasText(secretKey)) {
            throw new BusinessException("accessKey or secretKey 不能为空");
        }
        String valueAsString = objectMapper.writeValueAsString(options);
        QianFanChatOptions chatOptions = objectMapper.readValue(valueAsString, QianFanChatOptions.class);

        return new QianFanChatModel(new QianFanApi(apiKey, secretKey), chatOptions);
    }
}
