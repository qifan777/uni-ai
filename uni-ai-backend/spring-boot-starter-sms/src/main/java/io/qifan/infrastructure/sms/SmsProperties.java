package io.qifan.infrastructure.sms;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

enum Provider {
    NONE,
    ALI_YUN;
}

@Data
@ConfigurationProperties(prefix = "sms")
public class SmsProperties {
    private Provider provider;
}
