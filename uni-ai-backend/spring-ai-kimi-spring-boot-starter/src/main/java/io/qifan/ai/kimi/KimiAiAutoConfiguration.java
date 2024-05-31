package io.qifan.ai.kimi;

import io.qifan.ai.kimi.api.KimiAiApi;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executor;

@AutoConfiguration
@Configuration
@EnableConfigurationProperties(KimiAiProperties.class)
public class KimiAiAutoConfiguration {
    @ConditionalOnProperty(prefix = KimiAiProperties.CONFIG_PREFIX, name = "enabled", havingValue = "TRUE")
    @Bean(name = "kimiAiApi")
    public KimiAiApi kimiAiApi(KimiAiProperties kimiAiProperties, Executor executor) {
        return new KimiAiApi(kimiAiProperties.getApiKey(), executor);
    }

    @ConditionalOnProperty(prefix = KimiAiProperties.CONFIG_PREFIX, name = "enabled", havingValue = "TRUE")
    @Bean
    public KimiAiChatModel kimiAiChatModel(KimiAiApi kimiAiApi) {
        return new KimiAiChatModel(kimiAiApi);
    }
}
