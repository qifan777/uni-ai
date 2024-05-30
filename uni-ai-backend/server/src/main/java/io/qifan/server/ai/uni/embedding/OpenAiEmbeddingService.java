package io.qifan.server.ai.uni.embedding;

import io.qifan.infrastructure.common.exception.BusinessException;
import lombok.AllArgsConstructor;
import org.springframework.ai.autoconfigure.openai.OpenAiConnectionProperties;
import org.springframework.ai.document.MetadataMode;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.openai.OpenAiEmbeddingModel;
import org.springframework.ai.openai.OpenAiEmbeddingOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;

@Service
@AllArgsConstructor
public class OpenAiEmbeddingService implements UniAiEmbeddingService {
    private final OpenAiConnectionProperties openAiConnectionProperties;

    @Override
    public EmbeddingModel getEmbeddingModel(Map<String, Object> options) {
        String apiKey = (String) options.get("apiKey");
        String baseUrl = (String) options.get("baseUrl");
        if (!StringUtils.hasText(apiKey)) {
            apiKey = openAiConnectionProperties.getApiKey();
        }
        if (!StringUtils.hasText(baseUrl)) {
            baseUrl = openAiConnectionProperties.getBaseUrl();
        }
        if (!StringUtils.hasText(apiKey)) {
            throw new BusinessException("apiKey不能为空");
        }
        if (!StringUtils.hasText(baseUrl)) {
            throw new BusinessException("baseUrl不能为空");
        }
        String model = (String) options.get("model");
        if (!StringUtils.hasText(model)) {
            throw new BusinessException("model不能为空");
        }
        OpenAiEmbeddingOptions openAiEmbeddingOptions = new OpenAiEmbeddingOptions();
        openAiEmbeddingOptions.setModel(model);
        return new OpenAiEmbeddingModel(new OpenAiApi(baseUrl, apiKey), MetadataMode.EMBED, openAiEmbeddingOptions);
    }

}
