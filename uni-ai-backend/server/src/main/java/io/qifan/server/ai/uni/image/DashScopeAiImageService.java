package io.qifan.server.ai.uni.image;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.qifan.ai.dashscope.DashScopeAiImagModel;
import io.qifan.ai.dashscope.DashScopeAiImageOptions;
import io.qifan.ai.dashscope.DashScopeAiProperties;
import io.qifan.ai.dashscope.api.DashScopeAiImageApi;
import io.qifan.infrastructure.common.exception.BusinessException;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.ai.image.ImageModel;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;

@Service
@AllArgsConstructor
public class DashScopeAiImageService implements UniAiImageService {
    private final ObjectMapper objectMapper;
    private final DashScopeAiProperties dashScopeAiProperties;

    @SneakyThrows
    @Override
    public ImageModel getImageMode(Map<String, Object> options) {
        String apiKey = (String) options.get("apiKey");
        if (!StringUtils.hasText(apiKey)) {
            apiKey = dashScopeAiProperties.getApiKey();
        }
        if (!StringUtils.hasText(apiKey)) {
            throw new BusinessException("apiKey不能为空");
        }
        String valueAsString = objectMapper.writeValueAsString(options);
        DashScopeAiImageOptions imageOptions = objectMapper.readValue(valueAsString, DashScopeAiImageOptions.class);
        return new DashScopeAiImagModel(new DashScopeAiImageApi(apiKey), imageOptions);
    }
}
