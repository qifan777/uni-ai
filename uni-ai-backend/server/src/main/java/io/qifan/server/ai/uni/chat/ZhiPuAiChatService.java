package io.qifan.server.ai.uni.chat;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.qifan.infrastructure.common.exception.BusinessException;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.ai.autoconfigure.zhipuai.ZhiPuAiConnectionProperties;
import org.springframework.ai.chat.model.ChatModel;
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
    private final ObjectMapper objectMapper;

    @SneakyThrows
    @Override
    public ChatModel getChatModel(Map<String, Object> options) {
        String apiKey = (String) options.get("apiKey");
        if (!StringUtils.hasText(apiKey)) {
            apiKey = zhiPuAiConnectionProperties.getApiKey();
        }
        if (!StringUtils.hasText(apiKey)) {
            throw new BusinessException("apiKey不能为空");
        }
        String valueAsString = objectMapper.writeValueAsString(options);
        ZhiPuAiChatOptions chatOptions = objectMapper.readValue(valueAsString, ZhiPuAiChatOptions.class);
        return new ZhiPuAiChatModel(new ZhiPuAiApi(apiKey), chatOptions);
    }
}
