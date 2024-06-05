package io.qifan.server.oss;

import cn.dev33.satoken.stp.StpUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.qifan.server.dict.model.DictConstants;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.Map;

@Repository
@AllArgsConstructor
public class OSSRepository {
    public static final String OSS_SETTING = "oss:";
    private final StringRedisTemplate redisTemplate;
    private final ObjectMapper objectMapper;

    @SneakyThrows
    public void save(OSSSetting setting) {
        redisTemplate.opsForValue().set(OSS_SETTING + StpUtil.getLoginIdAsString(), objectMapper.writeValueAsString(setting));
    }

    @SneakyThrows
    public OSSSetting get() {
        String value = redisTemplate.opsForValue().get(OSS_SETTING + StpUtil.getLoginIdAsString());
        if (!StringUtils.hasText(value)) {
            return new OSSSetting().setType(DictConstants.OssType.ALI_YUN_OSS)
                    .setOptions(Map.of());
        }
        return objectMapper.readValue(value, OSSSetting.class);
    }
}
