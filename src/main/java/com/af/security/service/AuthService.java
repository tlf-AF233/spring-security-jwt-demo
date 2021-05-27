package com.af.security.service;

import com.af.security.modal.LoginRequest;

import java.util.Map;

/**
 * @author AF
 * @date 2021/5/12 22:40
 */
public interface AuthService {

    String login(LoginRequest loginRequest);

    String createRefreshToken(String token);

    Map<String, String> refreshToken(String refreshToken);
}
