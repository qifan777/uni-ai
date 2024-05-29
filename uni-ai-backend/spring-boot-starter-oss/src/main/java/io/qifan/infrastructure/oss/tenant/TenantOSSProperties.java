package io.qifan.infrastructure.oss.tenant;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "oss.tenant")
public class TenantOSSProperties {
    private String secretId;
    private String secretKey;
    private String region;
    private String bucket;
}
