package io.qifan.server.user.wechat.controller;

import cn.dev33.satoken.annotation.SaIgnore;
import cn.dev33.satoken.stp.SaTokenInfo;
import io.qifan.infrastructure.common.constants.ResultCode;
import io.qifan.infrastructure.common.exception.BusinessException;
import io.qifan.infrastructure.sms.SmsService;
import io.qifan.server.user.wechat.model.UserWeChatRegisterInput;
import io.qifan.server.user.wechat.model.UserWeChatRegisterInputV2;
import io.qifan.server.user.wechat.repository.UserWeChatRepository;
import io.qifan.server.user.wechat.service.UserWeChatService;
import lombok.AllArgsConstructor;
import org.babyfish.jimmer.client.meta.DefaultFetcherOwner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("front/user-we-chat")
@AllArgsConstructor
@DefaultFetcherOwner(UserWeChatRepository.class)
@Transactional
public class UserWeChatForFrontController {
    private final UserWeChatService userWeChatService;
    private final SmsService smsService;

    @SaIgnore
    @PostMapping("register")
    public SaTokenInfo register(@RequestBody @Validated UserWeChatRegisterInput registerInput) {
        boolean checked = smsService.checkSms(registerInput.getPhone(), registerInput.getCode());
        if (!checked) {
            throw new BusinessException(ResultCode.ValidateError, "验证码错误");
        }
        return userWeChatService.register(registerInput);
    }

    @SaIgnore
    @PostMapping("register2")
    public SaTokenInfo registerV2(@RequestBody @Validated UserWeChatRegisterInputV2 registerInputV2) {
        return userWeChatService.registerV2(registerInputV2);
    }
}
