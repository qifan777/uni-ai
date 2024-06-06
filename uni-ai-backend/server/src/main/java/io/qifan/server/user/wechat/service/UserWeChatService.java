package io.qifan.server.user.wechat.service;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.dev33.satoken.stp.SaLoginModel;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import io.qifan.server.infrastructure.model.LoginDevice;
import io.qifan.server.user.root.entity.User;
import io.qifan.server.user.root.entity.UserDraft;
import io.qifan.server.user.root.entity.UserFetcher;
import io.qifan.server.user.root.entity.UserTable;
import io.qifan.server.user.root.repository.UserRepository;
import io.qifan.server.user.wechat.entity.UserWeChat;
import io.qifan.server.user.wechat.entity.UserWeChatDraft;
import io.qifan.server.user.wechat.entity.UserWeChatFetcher;
import io.qifan.server.user.wechat.entity.UserWeChatTable;
import io.qifan.server.user.wechat.model.UserWeChatRegisterInput;
import io.qifan.server.user.wechat.model.UserWeChatRegisterInputV2;
import io.qifan.server.user.wechat.repository.UserWeChatRepository;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class UserWeChatService {

    private final UserWeChatRepository userWeChatRepository;
    private final UserRepository userRepository;
    private final WxMaService wxMaService;
    private final ApplicationEventPublisher eventPublisher;


    @SneakyThrows
    public SaTokenInfo register(UserWeChatRegisterInput registerInput) {
        UserWeChatTable t1 = UserWeChatTable.$;
        WxMaJscode2SessionResult session = wxMaService.getUserService()
                .getSessionInfo(registerInput.getLoginCode());
        String openid = session.getOpenid();
        UserWeChat userWeChat = userWeChatRepository.sql()
                .createQuery(t1)
                .where(t1.openId().eq(openid))
                .select(t1.fetch(UserWeChatFetcher.$.allScalarFields().user(UserFetcher.$.phone())))
                .fetchOptional()
                .orElseGet(() -> {
                    UserTable t2 = UserTable.$;
                    // 查询手机号对应的用户
                    User user = userRepository.sql().createQuery(t2)
                            .where(t2.phone().eq(registerInput.getPhone()))
                            .select(t2)
                            .fetchOptional()
                            // 手机号查询的用户为空,则说明该用户从未使用过起凡商城
                            .orElseGet(() -> {
                                return userRepository.save(UserDraft.$.produce(draft -> {
                                    draft.setNickname("微信用户")
                                            // 此处密码无需加密,
                                            .setPassword("123456")
                                            .setPhone(registerInput.getPhone());
                                }));
                            });
                    StpUtil.switchTo(user.id());
                    return userWeChatRepository.save(UserWeChatDraft.$.produce(draft -> {
                        draft.setUser(user)
                                .setOpenId(openid);
                    }));
                });
        StpUtil.login(userWeChat.user().id(), new SaLoginModel().setDevice(LoginDevice.MP_WECHAT)
                .setTimeout(60 * 60 * 24 * 30 * 36));
        return StpUtil.getTokenInfo();
    }

    @SneakyThrows
    public SaTokenInfo registerV2(UserWeChatRegisterInputV2 registerInputV2) {
        String phoneNumber = wxMaService.getUserService().getPhoneNoInfo(registerInputV2.getPhoneCode()).getPhoneNumber();
        UserWeChatRegisterInput userWeChatRegisterInput = new UserWeChatRegisterInput();
        userWeChatRegisterInput.setPhone(phoneNumber);
        userWeChatRegisterInput.setInviteCode(registerInputV2.getInviteCode());
        userWeChatRegisterInput.setLoginCode(registerInputV2.getLoginCode());
        return register(userWeChatRegisterInput);


    }
}