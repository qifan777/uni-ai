package io.qifan.infrastructure.oss.local;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;


@Data
@ConfigurationProperties(prefix = "oss.local")
public class LocalOSSProperties {

  private String path;
  private String url;
}
