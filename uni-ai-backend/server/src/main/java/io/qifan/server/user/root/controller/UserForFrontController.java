package io.qifan.server.user.root.controller;

import cn.dev33.satoken.annotation.SaCheckDisable;
import cn.dev33.satoken.annotation.SaIgnore;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import io.qifan.infrastructure.common.constants.ResultCode;
import io.qifan.infrastructure.common.exception.BusinessException;
import io.qifan.server.menu.entity.Menu;
import io.qifan.server.menu.entity.MenuTable;
import io.qifan.server.menu.repository.MenuRepository;
import io.qifan.server.user.root.entity.User;
import io.qifan.server.user.root.entity.UserDraft;
import io.qifan.server.user.root.entity.dto.UserInfoInput;
import io.qifan.server.user.root.entity.dto.UserLoginInput;
import io.qifan.server.user.root.entity.dto.UserRegisterInput;
import io.qifan.server.user.root.entity.dto.UserResetPasswordInput;
import io.qifan.server.user.root.repository.UserRepository;
import io.qifan.server.user.root.service.UserService;
import lombok.AllArgsConstructor;
import org.babyfish.jimmer.client.FetchBy;
import org.babyfish.jimmer.client.meta.DefaultFetcherOwner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("front/user")
@AllArgsConstructor
@DefaultFetcherOwner(UserRepository.class)
@Transactional
@SaCheckDisable
public class UserForFrontController {
    private final UserRepository userRepository;
    private final MenuRepository menuRepository;
    private final UserService userService;

    @GetMapping("info")
    public @FetchBy(value = "USER_ROLE_FETCHER") User getUserInfo() {
        return userRepository.findById(StpUtil.getLoginIdAsString(), UserRepository.USER_ROLE_FETCHER)
                .orElseThrow(() -> new BusinessException(ResultCode.NotFindError, "数据不存在"));
    }


    @GetMapping("menus")
    public List<@FetchBy(value = "SIMPLE_FETCHER", ownerType = MenuRepository.class) Menu> getUserMenus() {
        MenuTable t = MenuTable.$;
        return menuRepository.sql()
                .createQuery(t)
                .where(t
                        .roles(roleMenuRelTableEx -> roleMenuRelTableEx
                                .role()
                                .users(userRoleRelTableEx -> userRoleRelTableEx
                                        .user()
                                        .id()
                                        .eq(StpUtil.getLoginIdAsString()))))
                .where(t.visible().eq(true))
                .select(t.fetch(MenuRepository.SIMPLE_FETCHER))
                .execute();
    }

    @SaIgnore
    @PostMapping("register")
    public SaTokenInfo register(@RequestBody @Validated UserRegisterInput registerInput) {
        return userService.register(registerInput);
    }

    @SaIgnore
    @PostMapping("login")
    public SaTokenInfo login(@RequestBody @Validated UserLoginInput loginInput) {
        return userService.login(loginInput);
    }

    @SaIgnore
    @PutMapping("password")
    public SaTokenInfo passwordRest(@RequestBody @Validated UserResetPasswordInput restInput) {
        return userService.passwordRest(restInput);
    }

    @PostMapping("info")
    public String updateInfo(@RequestBody @Validated UserInfoInput restInput) {
        return userRepository.update(UserDraft.$.produce(restInput.toEntity(), draft -> draft.setId(StpUtil.getLoginIdAsString()))).id();
    }
}
