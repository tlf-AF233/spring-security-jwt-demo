package com.af.security.exception;

import com.af.security.enums.ErrorCode;
import lombok.Getter;

/**
 * 自定义异常基类
 * @author AF
 * @date 2021/5/13 19:34
 */
@Getter
public abstract class BaseException extends RuntimeException {

    private final ErrorCode errorCode;

    private final Object data;

    public BaseException(ErrorCode errorCode, Object data) {
        super(errorCode.getMsg());
        this.errorCode = errorCode;
        this.data = data;
    }

    public BaseException(ErrorCode errorCode) {
        this(errorCode, null);
    }


}
