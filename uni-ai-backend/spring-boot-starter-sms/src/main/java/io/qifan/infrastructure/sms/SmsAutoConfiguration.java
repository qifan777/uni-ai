package io.qifan.infrastructure.sms;

import io.qifan.infrastructure.sms.aliyun.AliYunSmsProperties;
import io.qifan.infrastructure.sms.aliyun.AliYunSmsService;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

@AutoConfiguration
@EnableConfigurationProperties(SmsProperties.class)
public class SmsAutoConfiguration {
    @Bean
    public SmsController smsController(SmsService service) {
        return new SmsController(service);
    }

    @Bean
    @ConditionalOnMissingBean(SmsService.class)
    @ConditionalOnProperty(prefix = "sms", name = "provider", havingValue = "NONE", matchIfMissing = true)
    public SmsService defaultSms(StringRedisTemplate redisTemplate) {
        return new DefaultSmsService(redisTemplate);
    }

    @Configuration
    @EnableConfigurationProperties(AliYunSmsProperties.class)
    @ConditionalOnProperty(prefix = "sms", name = "provider", havingValue = "ALI_YUN")
    static class AliYunSmsConfig {
        @Bean
        @ConditionalOnMissingBean(SmsService.class)
        public SmsService aliYunSms(AliYunSmsProperties aliYunSmsProperties, StringRedisTemplate redisTemplate) {
            return new AliYunSmsService(aliYunSmsProperties, redisTemplate);
        }
    }

}
