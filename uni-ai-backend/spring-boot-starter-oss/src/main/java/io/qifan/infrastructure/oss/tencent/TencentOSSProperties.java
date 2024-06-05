package io.qifan.infrastructure.oss.tencent;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "oss.tencent")
public class TencentOSSProperties {
    private String secretId;
    private String secretKey;
    private String region;
    private String bucket;
}
