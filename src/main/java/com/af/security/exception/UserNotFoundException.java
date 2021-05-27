package com.af.security.exception;

import com.af.security.enums.ErrorCode;

/**
 * 用户不存在异常
 * @author AF
 * @date 2021/5/14 11:39
 */
public class UserNotFoundException extends BaseException {
    public UserNotFoundException(Object data) {
        super(ErrorCode.USER_NOT_FOUND, data);
    }
}
