package io.qifan.ai.dashscope;

import io.qifan.ai.dashscope.api.DashScopeAiApi;
import org.springframework.ai.document.MetadataMode;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@AutoConfiguration
@Configuration
@EnableConfigurationProperties(DashScopeAiProperties.class)
public class DashScopeAiAutoConfiguration {
    @Bean
    @ConditionalOnProperty(prefix = DashScopeAiProperties.CONFIG_PREFIX, name = "enabled", havingValue = "TRUE")
    public DashScopeAiEmbeddingModel dashScopeAiEmbeddingClient(DashScopeAiApi dashScopeAiApi) {
        return new DashScopeAiEmbeddingModel(dashScopeAiApi, new DashScopeAiEmbeddingOptions(), MetadataMode.EMBED);
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
