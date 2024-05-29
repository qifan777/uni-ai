package io.qifan.server.setting;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class SettingRepository {
    private final RedisTemplate<String, String> redisTemplate;
    public static final String KEY = "setting";
    private final ObjectMapper objectMapper;

    @SneakyThrows
    public Setting get() {

        String o = redisTemplate.opsForValue().get(KEY);
        if (o == null) {
            return new Setting();
        }
        return objectMapper.readValue(o, Setting.class);
    }

    @SneakyThrows
    public void save(Setting setting) {
        redisTemplate.opsForValue().set(KEY, objectMapper.writeValueAsString(setting));
    }
}
