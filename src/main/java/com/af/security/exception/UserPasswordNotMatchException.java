package com.af.security.exception;


import com.af.security.enums.ErrorCode;

/**
 * 密码错误
 * @author AF
 * @date 2021/5/14 19:27
 */
public class UserPasswordNotMatchException extends BaseException {
    public UserPasswordNotMatchException(Object data) {
        super(ErrorCode.USER_INVALID, data);
    }
}
