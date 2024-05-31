package io.qifan.server.ai.uni.embedding;

import io.qifan.ai.qianfan.QianFanAiProperties;
import io.qifan.ai.qianfan.QifanAiEmbeddingModel;
import io.qifan.ai.qianfan.QifanAiEmbeddingOptions;
import io.qifan.ai.qianfan.api.QianFanApi;
import io.qifan.infrastructure.common.exception.BusinessException;
import lombok.AllArgsConstructor;
import org.springframework.ai.document.MetadataMode;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;

@Service
@AllArgsConstructor
public class QifanAiEmbeddingService implements UniAiEmbeddingService {
    private final QianFanAiProperties qianFanAiProperties;

    @Override
    public EmbeddingModel getEmbeddingModel(Map<String, Object> options) {
        String accessKey = (String) options.get("accessKey");
        String secretKey = (String) options.get("secretKey");
        if (!StringUtils.hasText(accessKey) || !StringUtils.hasText(secretKey)) {
            accessKey = qianFanAiProperties.getAccessKey();
            secretKey = qianFanAiProperties.getSecretKey();
        }
        if (!StringUtils.hasText(accessKey) || !StringUtils.hasText(secretKey)) {
            throw new BusinessException("accessKey or secretKey 不能为空");
        }
        String model = (String) options.get("model");
        if (!StringUtils.hasText(model)) {
            throw new BusinessException("model不能为空");
        }
        return new QifanAiEmbeddingModel(new QianFanApi(accessKey, secretKey), MetadataMode.EMBED, new QifanAiEmbeddingOptions()
                .setModel(model));
    }
}
