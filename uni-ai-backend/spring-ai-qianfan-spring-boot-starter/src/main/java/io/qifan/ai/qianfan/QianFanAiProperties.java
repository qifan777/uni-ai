package io.qifan.ai.qianfan;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@Data
@ConfigurationProperties(value = QianFanAiProperties.CONFIG_PREFIX)
public class QianFanAiProperties {
    public final static String CONFIG_PREFIX = "spring.ai.qian-fan";
    private String accessKey;
    private String secretKey;
    @NestedConfigurationProperty
    private QianFanAiChatOptions chat = new QianFanAiChatOptions();
    private Boolean enabled;
}
