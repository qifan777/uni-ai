package io.qifan.infrastructure.common.exception;

import io.qifan.infrastructure.common.constants.BaseEnum;
import io.qifan.infrastructure.common.constants.ResultCode;
import lombok.Data;

@Data
public class BusinessException extends RuntimeException {
    BaseEnum resultCode;

    public BusinessException(String msg) {
        super(msg);
        this.resultCode = ResultCode.Fail;
    }

    public BusinessException(BaseEnum resultCode) {
        super(resultCode.getName());
        this.resultCode = resultCode;
    }

    public BusinessException(BaseEnum resultCode, String msg) {
        super(msg);
        this.resultCode = resultCode;
    }
}
