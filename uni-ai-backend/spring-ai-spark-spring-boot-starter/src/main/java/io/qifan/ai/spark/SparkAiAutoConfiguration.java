package io.qifan.ai.spark;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.qifan.ai.spark.api.SparkAiApi;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executor;

@AutoConfiguration
@Configuration
@EnableConfigurationProperties(value = {SparkAiProperties.class})
public class SparkAiAutoConfiguration {

    @ConditionalOnProperty(prefix = SparkAiProperties.CONFIG_PREFIX, name = "enabled", havingValue = "TRUE")
    @Bean
    public SparkAiChatModel sparkAiChatModel(SparkAiApi sparkAiApi) {
        return new SparkAiChatModel(sparkAiApi);
    }

    @ConditionalOnProperty(prefix = SparkAiProperties.CONFIG_PREFIX, name = "enabled", havingValue = "TRUE")
    @Bean
    public SparkAiApi sparkAiApi(SparkAiProperties sparkAiProperties,
                                 ObjectMapper objectMapper, Executor executor) {
        return new SparkAiApi(
                sparkAiProperties.getApiKey(),
                sparkAiProperties.getApiSecret(),
                sparkAiProperties.getAppId(),
                objectMapper,
                executor);
    }

}
