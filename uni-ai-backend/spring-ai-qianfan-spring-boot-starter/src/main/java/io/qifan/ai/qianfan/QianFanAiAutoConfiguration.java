package io.qifan.ai.qianfan;

import io.qifan.ai.qianfan.api.QianFanApi;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executor;

@AutoConfiguration
@Configuration
@EnableConfigurationProperties(QianFanAiProperties.class)
public class QianFanAiAutoConfiguration {
    @Bean
    @ConditionalOnProperty(prefix = QianFanAiProperties.CONFIG_PREFIX, name = "enabled", havingValue = "TRUE")
    public QianFanAiChatModel qianFanAiChatModel(QianFanApi qianFanApi) {
        return new QianFanAiChatModel(qianFanApi);
    }


    @ConditionalOnProperty(prefix = QianFanAiProperties.CONFIG_PREFIX, name = "enabled", havingValue = "TRUE")
    @Bean
    public QianFanApi qianFanApi(QianFanAiProperties properties, Executor executor) {
        return new QianFanApi(properties.getAccessKey(), properties.getSecretKey(), executor);
    }

}
