package io.qifan.ai.spark;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@Data
@ConfigurationProperties(prefix = SparkAiProperties.CONFIG_PREFIX)
public class SparkAiProperties {
    public static final String CONFIG_PREFIX = "spring.ai.spark.chat";
    private String apiKey;
    private String apiSecret;
    private String appid;
    private boolean enabled = false;
    @NestedConfigurationProperty
    private SparkAiChatOptions options;
}
