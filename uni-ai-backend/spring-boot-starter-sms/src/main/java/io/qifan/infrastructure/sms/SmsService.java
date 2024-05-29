package io.qifan.infrastructure.sms;

import java.util.Random;

public interface SmsService {
    String SMS_KEY = "sms.";

    static String codeBuilder() {
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            code.append(new Random().nextInt(10));
        }
        return code.toString();
    }

    String sendSms(String phone);

    boolean checkSms(String phone, String code);
}
