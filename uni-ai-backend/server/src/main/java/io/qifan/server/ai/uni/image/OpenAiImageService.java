package io.qifan.server.ai.uni.image;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.qifan.infrastructure.common.exception.BusinessException;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.ai.autoconfigure.openai.OpenAiConnectionProperties;
import org.springframework.ai.image.ImageModel;
import org.springframework.ai.openai.OpenAiImageModel;
import org.springframework.ai.openai.OpenAiImageOptions;
import org.springframework.ai.openai.api.OpenAiImageApi;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClient;

import java.util.Map;

@Service
@AllArgsConstructor
public class OpenAiImageService implements UniAiImageService {
    private final ObjectMapper objectMapper;
    private final OpenAiConnectionProperties connectionProperties;

    @SneakyThrows
    @Override
    public ImageModel getImageMode(Map<String, Object> options) {
        String apiKey = (String) options.get("apiKey");
        String baseUrl = (String) options.get("baseUrl");
        if (!StringUtils.hasText(apiKey)) {
            apiKey = connectionProperties.getApiKey();
        }
        if (!StringUtils.hasText(baseUrl)) {
            baseUrl = connectionProperties.getBaseUrl();
        }
        if (!StringUtils.hasText(apiKey)) {
            throw new BusinessException("apiKey不能为空");
        }
        if (!StringUtils.hasText(baseUrl)) {
            throw new BusinessException("baseUrl不能为空");
        }
        String valueAsString = objectMapper.writeValueAsString(options);
        OpenAiImageOptions imageOptions = objectMapper.readValue(valueAsString, OpenAiImageOptions.class);

        return new OpenAiImageModel(new OpenAiImageApi(baseUrl, apiKey, RestClient.builder()), imageOptions, RetryTemplate.defaultInstance());
    }
}
