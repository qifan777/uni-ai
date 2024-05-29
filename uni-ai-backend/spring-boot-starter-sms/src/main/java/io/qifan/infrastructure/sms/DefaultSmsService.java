package io.qifan.infrastructure.sms;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@AllArgsConstructor
@Slf4j
public class DefaultSmsService implements SmsService {
    private final StringRedisTemplate redisTemplate;

    @Override
    public String sendSms(String phone) {
//        if (redisTemplate.opsForValue().get(SMS_KEY + phone) != null) {
//            throw new BusinessException(ResultCode.TransferStatusError, "120秒内请勿重复获取验证码");
//        }
        String code = SmsService.codeBuilder();
        redisTemplate.opsForValue().set(SMS_KEY + phone, code, 2, TimeUnit.MINUTES);
        log.info("验证码：{}", code);
        return code;
    }

    @Override
    public boolean checkSms(String phone, String code) {
        boolean equals = code.equals(redisTemplate.opsForValue().get(SMS_KEY + phone));
        if (equals) {
            redisTemplate.delete(SMS_KEY + phone);
        }
        return equals;
    }
}
