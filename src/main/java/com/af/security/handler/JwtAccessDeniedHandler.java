package com.af.security.handler;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 认证过的用户访问权限不足时，会调用此处理器
 * @author AF
 * @date 2021/5/18 20:41
 */
@Configuration
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    /**
     * 发送 403 拒绝访问
     * @param request
     * @param response
     * @param accessDeniedException
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        accessDeniedException = new AccessDeniedException("访问权限不足，拒绝访问");
        throw accessDeniedException;
    }
}
