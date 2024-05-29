package io.qifan.infrastructure.sms.aliyun;

import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.aliyun.teaopenapi.models.Config;
import io.qifan.infrastructure.common.constants.ResultCode;
import io.qifan.infrastructure.common.exception.BusinessException;
import io.qifan.infrastructure.sms.SmsService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
@AllArgsConstructor
public class AliYunSmsService implements SmsService {
    private final AliYunSmsProperties aliYunSMSProperties;
    private final StringRedisTemplate redisTemplate;

    @SneakyThrows
    public String sendSms(String phone) {
        if (redisTemplate.opsForValue().get(SMS_KEY + phone) != null) {
            throw new BusinessException(ResultCode.TransferStatusError, "120秒内请勿重复获取验证码");
        }
        String code = SmsService.codeBuilder();
        Config config = new Config().setAccessKeyId(aliYunSMSProperties.getAccessKeyId()).setAccessKeySecret(aliYunSMSProperties.getAccessKeySecret());
        Client client = new Client(config);
        config.endpoint = "dysmsapi.aliyuncs.com";
        SendSmsRequest sendSmsRequest = new SendSmsRequest().setSignName(aliYunSMSProperties.getSignName()).setTemplateCode(aliYunSMSProperties.getTemplateCode()).setTemplateParam("{\"code\": \"{0}\"}".replace("{0}", code)).setPhoneNumbers(phone);
        SendSmsResponse sendSmsResponse = client.sendSms(sendSmsRequest);
        if (!Objects.equals(sendSmsResponse.getBody().code, "OK")) {
            throw new BusinessException(ResultCode.BusinessError, sendSmsResponse.getBody().getCode() + "：" + sendSmsResponse.getBody().getMessage());
        }
        redisTemplate.opsForValue().set(SMS_KEY + phone, code, 2, TimeUnit.MINUTES);
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
