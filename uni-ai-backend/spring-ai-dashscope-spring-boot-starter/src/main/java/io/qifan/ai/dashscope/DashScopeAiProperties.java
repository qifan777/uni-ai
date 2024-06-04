package io.qifan.ai.dashscope;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@Data
@ConfigurationProperties(prefix = DashScopeAiProperties.CONFIG_PREFIX)
public class DashScopeAiProperties {
    public static final String CONFIG_PREFIX = "spring.ai.dash-scope";
    private String apiKey;
    private Boolean enabled;
    @NestedConfigurationProperty
    private DashScopeAiChatOptions chat = new DashScopeAiChatOptions();
    @NestedConfigurationProperty
    private DashScopeAiEmbeddingOptions embedding = new DashScopeAiEmbeddingOptions();
    @NestedConfigurationProperty
    private DashScopeAiImageOptions image = new DashScopeAiImageOptions();
}
