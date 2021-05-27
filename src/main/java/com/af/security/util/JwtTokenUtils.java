package com.af.security.util;

import com.af.security.constants.Constants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Jwt Token 工具类
 * @author AF
 * @date 2021/5/11 17:09
 */
@Component
@ConfigurationProperties(prefix = "token")
@Data
public class JwtTokenUtils {

    /**
     * 密钥
     */
    private String secret;

    /**
     * 过期时间
     */
    private long expiration;

    /**
     * refreshToken 的过期时间
     */
    private long refreshExpiration;

    /**
     * rememberMe 过期时间
     */
    private long expirationRememberMe;


    protected final long MILLS_MINUTE = 1000 * 60L;

    protected final long MILLS_DAY = 24 * 60 * MILLS_MINUTE;


    /**
     * 创建 Token
     * @param username 用户名
     * @param id 用户 id
     * @param roles 角色信息
     * @param rememberMe 是否记住我
     * @return token
     */
    public String createToken(String username, String id, List<String> roles, boolean rememberMe) {
        SecretKey secretKey = generateKey(secret);
        long exp = rememberMe ? expirationRememberMe * MILLS_DAY : expiration * MILLS_MINUTE;

        Date now = new Date();
        Date exp_time = new Date(now.getTime() + exp);

        // stringRedisTemplate.opsForValue().set(Constants.LOGIN_USER_KEY_PREFIX + id, token, exp_time.getTime(), TimeUnit.MILLISECONDS);
        return Jwts.builder()
                .setExpiration(exp_time)
                .setHeaderParam("type", Constants.TOKEN_TYPE)
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .setIssuedAt(now)
                .setIssuer("AF")
                .setId(id)
                .claim(Constants.CLAIMS_ROLE, String.join(",", roles))
                .setSubject(username)
                .compact();

    }

    /**
     * 获取令牌中的角色信息
     * @param token
     * @return
     */
    public List<String> getRoles(String token) {
        String roleString = (String) parseToken(token).get(Constants.CLAIMS_ROLE);
        return Arrays.stream(roleString.split(","))
                    .collect(Collectors.toList());
    }

    /**
     * 获取 token 中的用户 id
     * @param token
     * @return
     */
    public Long getId(String token) {
        return Long.valueOf(parseToken(token).getId());
    }

    /**
     * 获取拼接后的 用户 redis key
     * @param token
     * @return
     */
    public String getKey(String token) {
        return Constants.LOGIN_USER_KEY_PREFIX + parseToken(token).getId();
    }

    /**
     * 生成 refreshToken
     * @param token
     * @return
     */
    public String refreshToken(String token) {
        long now_time = parseToken(token).getIssuedAt().getTime();
        long exp_time = now_time + refreshExpiration * MILLS_DAY;
        return Jwts.builder()
                .setExpiration(new Date(exp_time))
                .setHeaderParam("type", Constants.TOKEN_TYPE)
                .signWith(generateKey(secret), SignatureAlgorithm.HS256)
                .setIssuedAt(new Date(now_time))
                .setIssuer("AF")
                .setId(parseToken(token).getId())
                .setSubject(parseToken(token).getSubject())
                .compact();
    }

    /**
     * 获取去掉前缀的 token
     * @param request
     * @return
     */
    public String getToken(HttpServletRequest request) {
        String token = request.getHeader(Constants.TOKEN_HEADER);
        if (null != token && !"".equals(token) && token.startsWith(Constants.TOKEN_PREFIX)) {
            return token.replace(Constants.TOKEN_PREFIX, "");
        }
        return null;
    }

    /**
     * 获取令牌中的用户名
     * @param token
     * @return
     */
    public String getUsername(String token) {
        return parseToken(token).getSubject();
    }

    /**
     * 令牌是否过期
     * @param token
     * @return true if expiration
     */
    public boolean isExpiration(String token) {
        return parseToken(token).getExpiration().before(new Date());
    }



    /**
     * 生成足够规范的密钥
     * @param secret  yml 文件中的 jwt.token.secret
     * @return SecretKey
     */
    private SecretKey generateKey(String secret) {
        return Keys.hmacShaKeyFor(DatatypeConverter.parseBase64Binary(secret));
    }

    /**
     * 解析 token，获取 claim
     * @param token token
     * @return
     */
    private Claims parseToken(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(generateKey(secret))
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }


}
