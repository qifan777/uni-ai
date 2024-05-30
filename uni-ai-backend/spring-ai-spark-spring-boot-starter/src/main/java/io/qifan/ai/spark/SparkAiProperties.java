package io.qifan.ai.spark;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = SparkAiProperties.CONFIG_PREFIX)
public class SparkAiProperties {
    public static final String CONFIG_PREFIX = "spring.ai.spark.chat";
    private String apiKey;
    private String apiSecret;
    private String appId;
    private boolean enabled = false;
}
