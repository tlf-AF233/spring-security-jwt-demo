package com.af.system.controller;

import com.af.security.modal.ResponseVO;
import com.af.security.util.JwtTokenUtils;
import com.af.security.util.ResponseUtils;
import com.af.system.service.MenuService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author AF
 * @date 2021/5/25 16:24
 */
@Api(tags = "路由接口")
@RestController
public class IndexController {

    @Autowired
    private MenuService menuService;

    @Autowired
    private JwtTokenUtils jwtTokenUtils;

    @Autowired
    private HttpServletRequest request;

    /**
     * 根据登录用户的权限获取路由视图
     * @return
     */
    @GetMapping("/router")
    public ResponseVO<Object> selectRouterMenuByUserId() {
        return ResponseUtils.success(menuService.selectRouterMenuByUserId(jwtTokenUtils.getId(jwtTokenUtils.getToken(request))));
    }
}
