package io.qifan.ai.dashscope;

import io.qifan.ai.dashscope.api.DashScopeAiApi;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import static com.alibaba.dashscope.tokenizers.Tokenization.Models.TEXT_EMBEDDING_V1;

@AutoConfiguration
@Configuration
@EnableConfigurationProperties(DashScopeAiProperties.class)
public class DashScopeAiAutoConfiguration {
    @Bean
    @ConditionalOnProperty(prefix = DashScopeAiProperties.CONFIG_PREFIX, name = "enabled", havingValue = "TRUE")
    @Primary
    public DashScopeAiEmbeddingModel dashScopeAiEmbeddingModel(DashScopeAiApi dashScopeAiApi) {
        return new DashScopeAiEmbeddingModel(dashScopeAiApi, new DashScopeAiEmbeddingOptions().setModel(TEXT_EMBEDDING_V1));
    }

    @ConditionalOnProperty(prefix = DashScopeAiProperties.CONFIG_PREFIX, name = "enabled", havingValue = "TRUE")
    @Bean
    public DashScopeAiVLChatModel dashScopeAiVLChatModel(DashScopeAiApi dashScopeAiApi) {
        return new DashScopeAiVLChatModel(dashScopeAiApi);
    }

    @ConditionalOnProperty(prefix = DashScopeAiProperties.CONFIG_PREFIX, name = "enabled", havingValue = "TRUE")
    @Bean
    public DashScopeAiChatModel dashScopeAiChatModel(DashScopeAiApi dashScopeAiApi) {
        return new DashScopeAiChatModel(dashScopeAiApi);
    }

    @ConditionalOnProperty(prefix = DashScopeAiProperties.CONFIG_PREFIX, name = "enabled", havingValue = "TRUE")
    @Bean
    public DashScopeAiApi dashScopeAiApi(DashScopeAiProperties properties) {
        return new DashScopeAiApi(properties.getApiKey());
    }
}
