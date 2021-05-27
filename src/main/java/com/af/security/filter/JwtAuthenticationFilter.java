package com.af.security.filter;

import com.af.security.constants.Constants;
import com.af.security.exception.TokenExpirationException;
import com.af.security.service.impl.UserDetailsServiceImpl;
import com.af.security.util.JwtTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * JWT 认证过滤器
 * @author AF
 * @date 2021/5/12 21:47
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenUtils jwtTokenUtils;

    @Autowired
    private UserDetailsService userDetailsService;

    /**
     * 处理过滤器的异常
     */
    @Autowired
    private HandlerExceptionResolver handlerExceptionResolver;

    /**
     * 请求会先经过此过滤器
     * @param request
     * @param response
     * @param chain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 获取 token
        String token = jwtTokenUtils.getToken(request);
        if (null == token) {
            SecurityContextHolder.clearContext();
            chain.doFilter(request, response);
            return;
        }
        if (jwtTokenUtils.isExpiration(token)) {
            // 抛出过期异常
            handlerExceptionResolver.resolveException(request, response, null, new TokenExpirationException("请刷新令牌后重试"));
            return;
        }
        // 将认证信息注册，此时再经过 UsernamePasswordAuthenticationFilter 就不会再判断是否登录
        UsernamePasswordAuthenticationToken authentication = getAuthentication(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }

    /**
     * 获取 Authentication 对象
     * @param token
     * @return
     */
    private UsernamePasswordAuthenticationToken getAuthentication(String token) {
        String username = jwtTokenUtils.getUsername(token);
        UserDetails loginUser = userDetailsService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(loginUser, token, loginUser.getAuthorities());
    }
}
