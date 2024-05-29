package io.qifan.infrastructure.common.constants;

import java.util.Arrays;
import java.util.Optional;

public enum ResultCode implements BaseEnum {
    Success(1, "操作成功"),
    Fail(0, "操作失败"),
    NotFindError(10001, "未查询到信息"),
    SaveError(10002, "保存信息失败"),
    UpdateError(10003, "更新信息失败"),
    ValidateError(10004, "数据检验失败"),
    StatusHasValid(10005, "状态已经被启用"),
    StatusHasInvalid(10006, "状态已经被禁用"),
    SystemError(10007, "系统异常"),
    BusinessError(10008, "业务异常"),
    ParamSetIllegal(10009, "参数设置非法"),
    TransferStatusError(10010, "当前状态不正确，请勿重复提交"),
    NotGrant(10011, "没有操作该功能的权限，请联系管理员");

    /**
     * code的取值规则，xx代表模块，xxx代表功能异常 例如：基础模块（10）的查询异常（001）
     */
    private final Integer code;
    /**
     * 异常信息
     */
    private final String name;

    ResultCode(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public static Optional<ResultCode> of(Integer code) {
        return Arrays.stream(ResultCode.values()).filter(resultCode -> resultCode.getCode().equals(code)).findFirst();
    }

    @Override
    public Integer getCode() {
        return this.code;
    }

    @Override
    public String getName() {
        return this.name;
    }

}
