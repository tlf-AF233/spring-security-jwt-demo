package com.af.security.exception;

import com.af.security.enums.ErrorCode;

/**
 * 用户不可用异常
 * @author AF
 * @date 2021/5/14 14:00
 */
public class UserUnableException extends BaseException {
    public UserUnableException(Object data) {
        super(ErrorCode.USER_UNABLE, data);
    }
}
