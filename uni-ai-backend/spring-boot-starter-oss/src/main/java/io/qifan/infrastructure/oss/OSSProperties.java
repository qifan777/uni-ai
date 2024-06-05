package io.qifan.infrastructure.oss;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

enum Provider {
    ALI_YUN,
    LOCAL,
    TENCENT;
}

@Data
@ConfigurationProperties(prefix = "oss")
public class OSSProperties {

    private Provider provider;
}
