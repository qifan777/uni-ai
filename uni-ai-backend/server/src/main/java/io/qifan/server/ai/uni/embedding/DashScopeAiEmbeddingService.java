package io.qifan.server.ai.uni.embedding;

import com.alibaba.cloud.ai.autoconfigure.dashscope.DashScopeEmbeddingProperties;
import com.alibaba.cloud.ai.dashscope.api.DashScopeApi;
import com.alibaba.cloud.ai.dashscope.embedding.DashScopeEmbeddingModel;
import com.alibaba.cloud.ai.dashscope.embedding.DashScopeEmbeddingOptions;
import io.qifan.infrastructure.common.exception.BusinessException;
import lombok.AllArgsConstructor;
import org.springframework.ai.document.MetadataMode;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;

@Service
@AllArgsConstructor
public class DashScopeAiEmbeddingService implements UniAiEmbeddingService {
    DashScopeEmbeddingProperties dashScopeEmbeddingProperties;

    @Override
    public EmbeddingModel getEmbeddingModel(Map<String, Object> options) {
        String apiKey = (String) options.get("apiKey");
        if (!StringUtils.hasText(apiKey)) {
            apiKey = dashScopeEmbeddingProperties.getApiKey();
        }
        if (!StringUtils.hasText(apiKey)) {
            throw new BusinessException("apiKey不能为空");
        }
        String model = (String) options.get("model");
        if (!StringUtils.hasText(model)) {
            throw new BusinessException("model不能为空");
        }

        return new DashScopeEmbeddingModel(new DashScopeApi(apiKey), MetadataMode.EMBED, new DashScopeEmbeddingOptions.Builder().withModel(model).build());
    }
}
