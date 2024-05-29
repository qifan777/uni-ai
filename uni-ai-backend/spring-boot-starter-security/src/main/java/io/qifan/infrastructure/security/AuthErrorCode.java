package io.qifan.infrastructure.security;


import com.fasterxml.jackson.annotation.JsonValue;
import io.qifan.infrastructure.common.constants.BaseEnum;

import java.util.Arrays;

public enum AuthErrorCode implements BaseEnum {

    USER_LOGIN(1001001, "登录错误"),
    USER_LOGIN_NOT_EXIST(1001002, "用户不存在"),
    USER_LOGIN_EXIST(1001003, "用户已存在"),
    USER_LOGIN_PASSWORD_ERROR(1001004, "密码错误"),
    USER_LOGIN_WECHAT_EXIST(1001005, "微信用户已存在"),
    USER_PERMISSION(1001006, "访问权限异常"),
    USER_PERMISSION_UNAUTHENTICATED(1001007, "请登录"),
    USER_PERMISSION_EXPIRED(1001008, "请重新登录"),
    USER_PERMISSION_UNAUTHORIZED(1001009, "权限不足"),
    USER_PASSWORD_REST(1001010, "请重置密码");;

    private final Integer code;
    private final String name;

    AuthErrorCode(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public static AuthErrorCode nameOf(String name) {
        return Arrays.stream(AuthErrorCode.values()).filter(type -> type.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("枚举不存在"));
    }

    public static AuthErrorCode codeOf(Integer code) {
        return Arrays.stream(AuthErrorCode.values()).filter(type -> type.getCode().equals(code))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("枚举不存在"));
    }

    @JsonValue
    @Override
    public Integer getCode() {
        return this.code;
    }

    @Override
    public String getName() {
        return this.name;
    }
}