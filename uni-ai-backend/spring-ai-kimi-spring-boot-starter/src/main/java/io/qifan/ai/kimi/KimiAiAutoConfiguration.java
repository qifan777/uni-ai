package io.qifan.ai.kimi;

import io.qifan.ai.kimi.api.KimiAiApi;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.web.client.RestClient;
import org.springframework.web.reactive.function.client.WebClient;

@AutoConfiguration
@Configuration
@EnableConfigurationProperties(KimiAiProperties.class)
public class KimiAiAutoConfiguration {
    @ConditionalOnProperty(prefix = KimiAiProperties.CONFIG_PREFIX, name = "enabled", havingValue = "TRUE")
    @Bean(name = "kimiAiApi")
    public KimiAiApi kimiAiApi(KimiAiProperties kimiAiProperties) {
        return new KimiAiApi(kimiAiProperties.getApiKey(), "https://api.moonshot.cn", RestClient.builder(), WebClient.builder());
    }

    @ConditionalOnProperty(prefix = KimiAiProperties.CONFIG_PREFIX, name = "enabled", havingValue = "TRUE")
    @Bean
    public KimiAiChatModel kimiAiChatModel(KimiAiApi kimiAiApi, KimiAiProperties properties) {
        return new KimiAiChatModel(kimiAiApi, properties.getChat(), RetryTemplate.defaultInstance());
    }
}
