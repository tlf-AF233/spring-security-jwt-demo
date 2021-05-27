package com.af.security.constants;

/**
 * 常量
 * @author AF
 * @date 2021/5/11 21:00
 */
public final class Constants {

    /**
     * JWT TOKEN
     */
    public static final String TOKEN_HEADER = "Authorization";

    public static final String TOKEN_PREFIX = "Bearer ";

    public static final String TOKEN_TYPE = "JWT";

    public static final String REFRESH_TOKEN_HEADER = "Refresh";

    /**
     * 角色信息的 key
     */
    public static final String CLAIMS_ROLE = "role";

    /**
     * Redis 存储的登录用户 key
     */
    public static final String LOGIN_USER_KEY_PREFIX = "login_user:";

    public static final Integer USER_UNABLE = 2;
}
