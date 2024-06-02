package io.qifan.server;

import io.qifan.server.infrastructure.model.WxPayPropertiesExtension;
import org.babyfish.jimmer.client.EnableImplicitApi;
import org.springframework.ai.autoconfigure.vectorstore.redis.RedisVectorStoreAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication(exclude = {RedisVectorStoreAutoConfiguration.class})
@EnableImplicitApi
@EnableConfigurationProperties(value = {WxPayPropertiesExtension.class})
@EnableAsync
public class ServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }
}
