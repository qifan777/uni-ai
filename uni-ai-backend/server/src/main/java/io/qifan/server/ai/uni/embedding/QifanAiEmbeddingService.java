package io.qifan.server.ai.uni.embedding;


import io.qifan.infrastructure.common.exception.BusinessException;
import lombok.AllArgsConstructor;
import org.springframework.ai.autoconfigure.qianfan.QianFanConnectionProperties;
import org.springframework.ai.document.MetadataMode;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.qianfan.QianFanEmbeddingModel;
import org.springframework.ai.qianfan.QianFanEmbeddingOptions;
import org.springframework.ai.qianfan.api.QianFanApi;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;

@Service
@AllArgsConstructor
public class QifanAiEmbeddingService implements UniAiEmbeddingService {
    private final QianFanConnectionProperties qianFanConnectionProperties;

    @Override
    public EmbeddingModel getEmbeddingModel(Map<String, Object> options) {
        String apiKey = (String) options.get("apiKey");
        String secretKey = (String) options.get("secretKey");
        if (!StringUtils.hasText(apiKey) || !StringUtils.hasText(secretKey)) {
            apiKey = qianFanConnectionProperties.getApiKey();
            secretKey = qianFanConnectionProperties.getSecretKey();
        }
        if (!StringUtils.hasText(apiKey) || !StringUtils.hasText(secretKey)) {
            throw new BusinessException("accessKey or secretKey 不能为空");
        }
        String model = (String) options.get("model");
        if (!StringUtils.hasText(model)) {
            throw new BusinessException("model不能为空");
        }
        return new QianFanEmbeddingModel(new QianFanApi(apiKey, secretKey),
                MetadataMode.EMBED,
                QianFanEmbeddingOptions.builder().withModel(model).build());
    }
}
