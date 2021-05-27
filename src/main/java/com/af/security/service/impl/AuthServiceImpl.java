package com.af.security.service.impl;

import com.af.security.constants.Constants;
import com.af.security.exception.BaseException;
import com.af.security.exception.TokenExpirationException;
import com.af.security.exception.UserPasswordNotMatchException;
import com.af.security.modal.LoginRequest;
import com.af.security.modal.LoginUser;
import com.af.security.service.AuthService;
import com.af.security.util.JwtTokenUtils;
import com.af.system.entity.Role;
import com.af.system.entity.User;
import com.af.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author AF
 * @date 2021/5/12 22:42
 */
@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private JwtTokenUtils jwtTokenUtils;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public String login(LoginRequest loginRequest) {
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();
        Authentication authentication = null;
        try {
            // 此处会调用 userDetailsServiceImpl.loadUserByUsername() 方法
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (BadCredentialsException e) {
            throw new UserPasswordNotMatchException(e.getMessage());
        }
        return createToken(loginRequest);
    }

    /**
     * 获取 refreshToken
     * @param token
     * @return
     */
    @Override
    public String createRefreshToken(String token) {
        return jwtTokenUtils.refreshToken(token);
    }

    /**
     * 刷新 token，返回新的 token
     * @param refreshToken
     * @return
     */
    @Override
    public Map<String, String> refreshToken(String refreshToken) {
        if (null == refreshToken || jwtTokenUtils.isExpiration(refreshToken)) {
            throw new TokenExpirationException("令牌已过期，请重新登录！");
        }
        String username = jwtTokenUtils.getUsername(refreshToken);
        User user = userService.selectUserByUsername(username);
        LoginRequest loginRequest = new LoginRequest(username, user.getPassword(), false);
        // 生成新的 token
        String token = createToken(loginRequest);
        // 生成新的 refreshToken
        String newRefreshToken = jwtTokenUtils.refreshToken(token);
        Map<String, String> map = new HashMap<>();
        map.put(Constants.TOKEN_HEADER, Constants.TOKEN_PREFIX + token);
        map.put(Constants.REFRESH_TOKEN_HEADER, newRefreshToken);
        return map;
    }



    private String createToken(LoginRequest loginRequest) {
        User currentUser = userService.selectUserByUsername(loginRequest.getUsername());
        List<Role> roleList = currentUser.getRoleList();
        List<String> roles = roleList.stream()
                .map(Role::getRoleCode)
                .collect(Collectors.toList());
        return jwtTokenUtils.createToken(currentUser.getUsername(), currentUser.getUserId().toString(), roles, loginRequest.getRememberMe());
    }

}
