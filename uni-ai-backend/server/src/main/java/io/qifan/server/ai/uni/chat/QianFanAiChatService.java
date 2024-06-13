package io.qifan.server.ai.uni.chat;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.qifan.ai.qianfan.QianFanAiChatModel;
import io.qifan.ai.qianfan.QianFanAiChatOptions;
import io.qifan.ai.qianfan.QianFanAiProperties;
import io.qifan.ai.qianfan.api.QianFanApi;
import io.qifan.infrastructure.common.exception.BusinessException;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.model.function.FunctionCallbackContext;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class QianFanAiChatService implements UniAiChatService {
    private final QianFanAiProperties qianFanAiProperties;
    private final ObjectMapper objectMapper;
    private final FunctionCallbackContext functionCallbackContext;

    @SneakyThrows
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
        String valueAsString = objectMapper.writeValueAsString(options);
        QianFanAiChatOptions chatOptions = objectMapper.readValue(valueAsString, QianFanAiChatOptions.class);
        if (options.containsKey("functions") && options.get("functions") instanceof List functions) {
            chatOptions.setFunctions(new HashSet<>(functions));
        }
        return new QianFanAiChatModel(functionCallbackContext, new QianFanApi(accessKey, secretKey), chatOptions);
    }
}
