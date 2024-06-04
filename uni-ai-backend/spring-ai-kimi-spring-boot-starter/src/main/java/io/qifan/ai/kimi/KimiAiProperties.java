package io.qifan.ai.kimi;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@Data
@ConfigurationProperties(prefix = KimiAiProperties.CONFIG_PREFIX)
public class KimiAiProperties {
    public static final String CONFIG_PREFIX = "spring.ai.kimi";
    private String apiKey;
    private Boolean enabled;
    @NestedConfigurationProperty
    private KimiAiChatOptions chat = new KimiAiChatOptions();
}
