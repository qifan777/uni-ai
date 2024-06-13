package io.qifan.ai.qianfan;

import io.qifan.ai.qianfan.api.QianFanApi;
import org.springframework.ai.document.MetadataMode;
import org.springframework.ai.model.function.FunctionCallbackContext;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@AutoConfiguration
@Configuration
@EnableConfigurationProperties(QianFanAiProperties.class)
public class QianFanAiAutoConfiguration {

    @Bean
    @ConditionalOnProperty(prefix = QianFanAiProperties.CONFIG_PREFIX, name = "enabled", havingValue = "TRUE")
    public QianFanAiChatModel qianFanAiChatModel(FunctionCallbackContext functionCallbackContext, QianFanApi qianFanApi, QianFanAiProperties properties) {
        return new QianFanAiChatModel(functionCallbackContext, qianFanApi, properties.getChat());
    }


    @ConditionalOnProperty(prefix = QianFanAiProperties.CONFIG_PREFIX, name = "enabled", havingValue = "TRUE")
    @Bean
    public QianFanApi qianFanApi(QianFanAiProperties properties) {
        return new QianFanApi(properties.getAccessKey(), properties.getSecretKey());
    }

    @ConditionalOnProperty(prefix = QianFanAiProperties.CONFIG_PREFIX, name = "enabled", havingValue = "TRUE")
    @Bean
    public QifanAiEmbeddingModel qifanAiEmbeddingModel(QianFanApi qianFanApi) {
        return new QifanAiEmbeddingModel(qianFanApi, MetadataMode.EMBED, new QifanAiEmbeddingOptions().setModel("Embedding-V1"));
    }

    @Bean
    @ConditionalOnMissingBean
    public FunctionCallbackContext springAiFunctionManager(ApplicationContext context) {
        FunctionCallbackContext manager = new FunctionCallbackContext();
        manager.setApplicationContext(context);
        return manager;
    }

}
