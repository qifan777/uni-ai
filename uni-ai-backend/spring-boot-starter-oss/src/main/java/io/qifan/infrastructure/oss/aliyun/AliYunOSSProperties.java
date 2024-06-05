package io.qifan.infrastructure.oss.aliyun;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "oss.ali-yun")
public class AliYunOSSProperties {
    @JsonProperty("endpoint")
    private String endpoint;
    @JsonProperty("bucketName")
    private String bucketName;
    @JsonProperty("accessKeyId")
    private String accessKeyId;
    @JsonProperty("accessKeySecret")
    private String accessKeySecret;
}
