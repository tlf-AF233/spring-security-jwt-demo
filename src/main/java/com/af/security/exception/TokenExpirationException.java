package com.af.security.exception;

import com.af.security.enums.ErrorCode;

/**
 * 令牌过期
 * @author AF
 * @date 2021/5/15 16:54
 */
public class TokenExpirationException extends BaseException {
    public TokenExpirationException(Object data) {
        super(ErrorCode.TOKEN_EXPIRATION, data);
    }
}
