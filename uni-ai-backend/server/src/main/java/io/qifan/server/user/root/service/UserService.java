package io.qifan.server.user.root.service;

import cn.dev33.satoken.annotation.SaIgnore;
import cn.dev33.satoken.secure.BCrypt;
import cn.dev33.satoken.stp.SaLoginModel;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import io.qifan.infrastructure.common.constants.ResultCode;
import io.qifan.infrastructure.common.exception.BusinessException;
import io.qifan.infrastructure.security.AuthErrorCode;
import io.qifan.infrastructure.sms.SmsService;
import io.qifan.server.infrastructure.model.LoginDevice;
import io.qifan.server.role.entity.Role;
import io.qifan.server.role.repository.RoleRepository;
import io.qifan.server.user.root.entity.*;
import io.qifan.server.user.root.entity.dto.UserLoginInput;
import io.qifan.server.user.root.entity.dto.UserRegisterInput;
import io.qifan.server.user.root.entity.dto.UserResetPasswordInput;
import io.qifan.server.user.root.repository.UserRepository;
import io.qifan.server.user.root.repository.UserRoleRelRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final SmsService smsService;
    private final RoleRepository roleRepository;
    private final UserRoleRelRepository userRoleRelRepository;

    @SaIgnore

    public SaTokenInfo register(UserRegisterInput registerInput) {
//        boolean checked = smsService.checkSms(registerInput.getPhone(), registerInput.getCode());
//        if (!checked) {
//            throw new BusinessException(ResultCode.ValidateError, "验证码错误");
//        }
        UserTable userTable = UserTable.$;
        userRepository.sql().createQuery(userTable)
                .where(userTable.phone().eq(registerInput.getPhone()))
                .select(userTable).fetchOptional()
                .ifPresent((user) -> {
                    throw new BusinessException(ResultCode.StatusHasValid, "用户已经存在");
                });

        User user = userRepository.save(UserDraft.$.produce(registerInput.toEntity(), draft -> {
            draft.setNickname("默认用户").setPassword(BCrypt.hashpw(draft.password()));
        }));
        StpUtil.login(user.id(), new SaLoginModel()
                .setDevice(LoginDevice.BROWSER)
                .setTimeout(60 * 60 * 24 * 30 * 36));
        Role role = roleRepository.findRoleByName("普通用户").orElseThrow(() -> new BusinessException("角色不存在，清联系管理员"));
        userRoleRelRepository.save(UserRoleRelDraft.$.produce(draft -> {
            draft.setRoleId(role.id())
                    .setUserId(user.id());
        }));
        return StpUtil.getTokenInfo();
    }

    public SaTokenInfo login(UserLoginInput loginInput) {
        UserTable userTable = UserTable.$;
        // 从数据库去根据手机号找到相应的用户
        User databaseUser = userRepository.sql().createQuery(userTable)
                .where(userTable.phone().eq(loginInput.getPhone()))
                .select(userTable.fetch(UserFetcher.$.allScalarFields()))
                .fetchOptional()
                .orElseThrow(() -> new BusinessException(AuthErrorCode.USER_LOGIN_NOT_EXIST));
        if (databaseUser.password().equals("123456")) {
            throw new BusinessException(AuthErrorCode.USER_PASSWORD_REST);
        }
        // 将请求用户的密码与数据库密码进行比对
        // BCrypt
        if (!BCrypt.checkpw(loginInput.getPassword(), databaseUser.password())) {
            throw new BusinessException(AuthErrorCode.USER_LOGIN_PASSWORD_ERROR);
        }
        // 生成token记录
        StpUtil.login(databaseUser.id(), new SaLoginModel()
                .setDevice(LoginDevice.BROWSER)
                .setTimeout(60 * 60 * 24 * 30 * 36));
        return StpUtil.getTokenInfo();
    }

    public SaTokenInfo passwordRest(UserResetPasswordInput restInput) {
        boolean checked = smsService.checkSms(restInput.getPhone(), restInput.getCode());
        if (!checked) {
            throw new BusinessException(ResultCode.ValidateError, "验证码错误");
        }
        User save = userRepository.save(UserDraft.$.produce(restInput.toEntity(), draft -> {
            draft.setPassword(BCrypt.hashpw(draft.password()));
        }));
        StpUtil.login(save.id(), new SaLoginModel()
                .setDevice(LoginDevice.BROWSER)
                .setTimeout(60 * 60 * 24 * 30 * 36));
        return StpUtil.getTokenInfo();
    }


}