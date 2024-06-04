package io.qifan.server.ai.uni.image;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.qifan.infrastructure.common.exception.BusinessException;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.ai.autoconfigure.zhipuai.ZhiPuAiConnectionProperties;
import org.springframework.ai.image.ImageModel;
import org.springframework.ai.zhipuai.ZhiPuAiImageModel;
import org.springframework.ai.zhipuai.ZhiPuAiImageOptions;
import org.springframework.ai.zhipuai.api.ZhiPuAiImageApi;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;

@AllArgsConstructor
@Service
public class ZhiPuAiImageService implements UniAiImageService {
    private final ObjectMapper objectMapper;
    private final ZhiPuAiConnectionProperties connectionProperties;

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
        ZhiPuAiImageOptions imageOptions = objectMapper.readValue(valueAsString, ZhiPuAiImageOptions.class);
        return new ZhiPuAiImageModel(new ZhiPuAiImageApi(apiKey), imageOptions, RetryTemplate.defaultInstance());
    }
}
