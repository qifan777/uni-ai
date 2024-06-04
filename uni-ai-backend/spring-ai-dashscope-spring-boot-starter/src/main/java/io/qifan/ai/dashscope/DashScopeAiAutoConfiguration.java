package io.qifan.ai.dashscope;

import io.qifan.ai.dashscope.api.DashScopeAiApi;
import io.qifan.ai.dashscope.api.DashScopeAiImageApi;
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
    public DashScopeAiVLChatModel dashScopeAiVLChatModel(DashScopeAiApi dashScopeAiApi, DashScopeAiProperties properties) {
        return new DashScopeAiVLChatModel(dashScopeAiApi, properties.getChat());
    }

    @ConditionalOnProperty(prefix = DashScopeAiProperties.CONFIG_PREFIX, name = "enabled", havingValue = "TRUE")
    @Bean
    public DashScopeAiChatModel dashScopeAiChatModel(DashScopeAiApi dashScopeAiApi, DashScopeAiProperties properties) {
        return new DashScopeAiChatModel(dashScopeAiApi, properties.getChat());
    }

    @ConditionalOnProperty(prefix = DashScopeAiProperties.CONFIG_PREFIX, name = "enabled", havingValue = "TRUE")
    @Bean
    public DashScopeAiApi dashScopeAiApi(DashScopeAiProperties properties) {
        return new DashScopeAiApi(properties.getApiKey());
    }

    @ConditionalOnProperty(prefix = DashScopeAiProperties.CONFIG_PREFIX, name = "enabled", havingValue = "TRUE")
    @Bean
    public DashScopeAiImageApi dashScopeAiImageApi(DashScopeAiProperties properties) {
        return new DashScopeAiImageApi(properties.getApiKey());
    }


}
