package io.qifan.infrastructure.sms;

import io.qifan.infrastructure.common.model.R;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("sms")
public class SmsController {
    private final SmsService smsService;

    @PostMapping("send")
    public R<Boolean> sendSms(@RequestParam String phone) {
        smsService.sendSms(phone);
        return R.ok(true);
    }
}
