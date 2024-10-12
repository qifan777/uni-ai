package io.qifan.server.ai.uni.image;

import com.alibaba.cloud.ai.autoconfigure.dashscope.DashScopeConnectionProperties;
import com.alibaba.cloud.ai.dashscope.api.DashScopeImageApi;
import com.alibaba.cloud.ai.dashscope.image.DashScopeImageModel;
import com.alibaba.cloud.ai.dashscope.image.DashScopeImageOptions;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.qifan.infrastructure.common.exception.BusinessException;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.ai.image.ImageModel;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;

@Service
@AllArgsConstructor
public class DashScopeAiImageService implements UniAiImageService {
    private final ObjectMapper objectMapper;
    private final DashScopeConnectionProperties connectionProperties;

    @SneakyThrows
    @Override
    public ImageModel getImageMode(Map<String, Object> options) {
        String apiKey = (String) options.get("apiKey");
        if (!StringUtils.hasText(apiKey)) {
            apiKey = connectionProperties.getApiKey();
        }
        if (!StringUtils.hasText(apiKey)) {
            throw new BusinessException("apiKey不能为空");
        }
        String valueAsString = objectMapper.writeValueAsString(options);
        DashScopeImageOptions imageOptions = objectMapper.readValue(valueAsString, DashScopeImageOptions.class);
        return new DashScopeImageModel(new DashScopeImageApi(apiKey), imageOptions, RetryTemplate.defaultInstance());
    }
}
