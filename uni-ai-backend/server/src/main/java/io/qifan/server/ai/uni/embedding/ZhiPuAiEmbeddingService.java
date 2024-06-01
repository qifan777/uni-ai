package io.qifan.server.ai.uni.embedding;

import io.qifan.infrastructure.common.exception.BusinessException;
import lombok.AllArgsConstructor;
import org.springframework.ai.autoconfigure.zhipuai.ZhiPuAiConnectionProperties;
import org.springframework.ai.document.MetadataMode;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.zhipuai.ZhiPuAiEmbeddingModel;
import org.springframework.ai.zhipuai.ZhiPuAiEmbeddingOptions;
import org.springframework.ai.zhipuai.api.ZhiPuAiApi;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;

@Service
@AllArgsConstructor
public class ZhiPuAiEmbeddingService implements UniAiEmbeddingService {
    private final ZhiPuAiConnectionProperties zhiPuAiConnectionProperties;

    @Override
    public EmbeddingModel getEmbeddingModel(Map<String, Object> options) {
        String apiKey = (String) options.get("apiKey");
        if (!StringUtils.hasText(apiKey)) {
            apiKey = zhiPuAiConnectionProperties.getApiKey();
        }
        if (!StringUtils.hasText(apiKey)) {
            throw new BusinessException("apiKey不能为空");
        }
        String model = (String) options.get("model");
        if (!StringUtils.hasText(model)) {
            throw new BusinessException("model不能为空");
        }

        return new ZhiPuAiEmbeddingModel(new ZhiPuAiApi(apiKey), MetadataMode.EMBED, ZhiPuAiEmbeddingOptions.builder()
                .withModel(model).build());
    }
}
