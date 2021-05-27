package com.af.security.exception;

import com.af.security.enums.ErrorCode;

/**
 * 其他自定义异常
 * @author AF
 * @date 2021/5/22 16:36
 */
public class CustomException extends BaseException {

    public CustomException(ErrorCode errorCode, Object data) {
        super(errorCode, data);
    }

    public CustomException(ErrorCode errorCode) {
        super(errorCode);
    }

}
