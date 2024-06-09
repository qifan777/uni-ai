package io.qifan.server.ai.uni.chat;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.qifan.infrastructure.common.exception.BusinessException;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.ai.autoconfigure.zhipuai.ZhiPuAiConnectionProperties;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.model.function.FunctionCallbackContext;
import org.springframework.ai.zhipuai.ZhiPuAiChatModel;
import org.springframework.ai.zhipuai.ZhiPuAiChatOptions;
import org.springframework.ai.zhipuai.api.ZhiPuAiApi;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class ZhiPuAiChatService implements UniAiChatService {
    private final ZhiPuAiConnectionProperties zhiPuAiConnectionProperties;
    private final ObjectMapper objectMapper;
    private final FunctionCallbackContext functionCallbackContext;

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
        if (options.get("functions") != null) {
            chatOptions.setFunctions(new HashSet<>((List<String>) options.get("functions")));
        }
        return new ZhiPuAiChatModel(new ZhiPuAiApi(apiKey), chatOptions, functionCallbackContext, RetryTemplate.defaultInstance());
    }
}
