package io.qifan.server.oss;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.qifan.infrastructure.oss.OSSAutoConfiguration;
import io.qifan.infrastructure.oss.service.OSSService;
import io.qifan.infrastructure.oss.tencent.TencentOSSProperties;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.ai.model.ModelOptionsUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service(value = "TENCENT_OSS")
@AllArgsConstructor
public class UniTencentSSService implements UniOSSService {
    private final ObjectMapper objectMapper;
    private final TencentOSSProperties properties;

    @SneakyThrows
    @Override
    public OSSService getOSSService(Map<String, Object> oss) {
        String valueAsString = objectMapper.writeValueAsString(oss);
        TencentOSSProperties ossProperties = objectMapper.readValue(valueAsString, TencentOSSProperties.class);
        TencentOSSProperties merge = ModelOptionsUtils.merge(ossProperties, properties, TencentOSSProperties.class);
        OSSAutoConfiguration.TencentConfig tenantConfig = new OSSAutoConfiguration.TencentConfig();
        return tenantConfig.tencentOSSService(tenantConfig.tencentOSS(merge), merge);
    }
}
