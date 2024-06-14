package io.qifan.ai.dashscope;

import io.qifan.ai.dashscope.api.DashScopeAiApi;
import io.qifan.ai.dashscope.api.DashScopeAiImageApi;
import org.springframework.ai.model.function.FunctionCallbackContext;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@AutoConfiguration
@Configuration
@EnableConfigurationProperties(DashScopeAiProperties.class)
public class DashScopeAiAutoConfiguration {
    @Bean
    @ConditionalOnProperty(prefix = DashScopeAiProperties.CONFIG_PREFIX, name = "enabled", havingValue = "TRUE")
    @Primary
    public DashScopeAiEmbeddingModel dashScopeAiEmbeddingModel(DashScopeAiApi dashScopeAiApi, DashScopeAiProperties properties) {
        return new DashScopeAiEmbeddingModel(dashScopeAiApi, properties.getEmbedding());
    }

    @ConditionalOnProperty(prefix = DashScopeAiProperties.CONFIG_PREFIX, name = "enabled", havingValue = "TRUE")
    @Bean
    public DashScopeAiVLChatModel dashScopeAiVLChatModel(DashScopeAiApi dashScopeAiApi, DashScopeAiProperties properties) {
        return new DashScopeAiVLChatModel(dashScopeAiApi, properties.getChat());
    }

    @ConditionalOnProperty(prefix = DashScopeAiProperties.CONFIG_PREFIX, name = "enabled", havingValue = "TRUE")
    @Bean
    public DashScopeAiChatModel dashScopeAiChatModel(FunctionCallbackContext functionCallbackContext, DashScopeAiApi dashScopeAiApi, DashScopeAiProperties properties) {
        return new DashScopeAiChatModel(functionCallbackContext, dashScopeAiApi, properties.getChat());
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

    @Bean
    @ConditionalOnMissingBean
    public FunctionCallbackContext springAiFunctionManager(ApplicationContext context) {
        FunctionCallbackContext manager = new FunctionCallbackContext();
        manager.setApplicationContext(context);
        return manager;
    }
}
