package io.qifan.server.ai.uni.embedding;

import io.qifan.ai.dashscope.DashScopeAiEmbeddingModel;
import io.qifan.ai.dashscope.DashScopeAiEmbeddingOptions;
import io.qifan.ai.dashscope.DashScopeAiProperties;
import io.qifan.ai.dashscope.api.DashScopeAiApi;
import io.qifan.infrastructure.common.exception.BusinessException;
import lombok.AllArgsConstructor;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;

@Service
@AllArgsConstructor
public class DashScopeAiEmbeddingService implements UniAiEmbeddingService {
    DashScopeAiProperties dashScopeAiProperties;

    @Override
    public EmbeddingModel getEmbeddingModel(Map<String, Object> options) {
        String apiKey = (String) options.get("apiKey");
        if (!StringUtils.hasText(apiKey)) {
            apiKey = dashScopeAiProperties.getApiKey();
        }
        if (!StringUtils.hasText(apiKey)) {
            throw new BusinessException("apiKey不能为空");
        }
        String model = (String) options.get("model");
        if (!StringUtils.hasText(model)) {
            throw new BusinessException("model不能为空");
        }

        return new DashScopeAiEmbeddingModel(new DashScopeAiApi(apiKey), new DashScopeAiEmbeddingOptions()
                .setModel(model));
    }
}
