package io.qifan.server.oss;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.qifan.infrastructure.oss.OSSAutoConfiguration;
import io.qifan.infrastructure.oss.aliyun.AliYunOSSProperties;
import io.qifan.infrastructure.oss.service.OSSService;
import io.qifan.server.dict.model.DictConstants;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.ai.model.ModelOptionsUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service(value = "ALI_YUN_OSS")
@AllArgsConstructor
public class UniAliYunOSSService implements UniOSSService {
    private final AliYunOSSProperties properties;
    private final ObjectMapper objectMapper;

    @SneakyThrows
    @Override
    public OSSService getOSSService(Map<String, Object> oss) {
        String valueAsString = objectMapper.writeValueAsString(oss);
        AliYunOSSProperties aliYunOSSProperties = objectMapper.readValue(valueAsString, AliYunOSSProperties.class);
        AliYunOSSProperties merge = ModelOptionsUtils.merge(aliYunOSSProperties, properties, AliYunOSSProperties.class);
        OSSAutoConfiguration.AliYunConfig aliYunConfig = new OSSAutoConfiguration.AliYunConfig();
        return aliYunConfig.aliYunOSSService(aliYunConfig.aliYunOSS(merge), merge);
    }
}
