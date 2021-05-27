package com.af.security.controller;

import com.af.security.constants.Constants;
import com.af.security.modal.LoginRequest;
import com.af.security.modal.ResponseVO;
import com.af.security.service.AuthService;
import com.af.security.util.ResponseUtils;
import com.af.system.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author AF
 * @date 2021/5/12 22:23
 */
@Api(tags = "认证接口")
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;

    /**
     * 登录接口
     * 返回 access_token 和 refresh_token
     * @param loginRequest
     * @return
     */
    @ApiOperation("登录")
    @PostMapping("/login")
    public ResponseVO<Object> login(@RequestBody LoginRequest loginRequest) {
        String token = authService.login(loginRequest);
        String refreshToken = authService.createRefreshToken(token);
        Map<String, String> map = new HashMap<>();
        map.put(Constants.TOKEN_HEADER, Constants.TOKEN_PREFIX + token);
        map.put(Constants.REFRESH_TOKEN_HEADER, refreshToken);
        userService.setLoginTime(loginRequest.getUsername());
        return ResponseUtils.success(map);
    }

    /**
     * 刷新 Token 接口
     * @param request
     * @return 新的 access_token 和 新的 refresh_token
     */
    @ApiOperation("刷新 Token")
    @GetMapping("/refreshToken")
    public ResponseVO<Object> refreshToken(HttpServletRequest request) {
        String refreshToken = request.getHeader(Constants.REFRESH_TOKEN_HEADER);
        Map<String, String> map = authService.refreshToken(refreshToken);
        return ResponseUtils.success(map);
    }

    @PreAuthorize("@ps.hasPerm('sys:user:list')")
    @GetMapping("/get")
    public ResponseVO<Object> get() {
        return ResponseUtils.success();
    }
}
