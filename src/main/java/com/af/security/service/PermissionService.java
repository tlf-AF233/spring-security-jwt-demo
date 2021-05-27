package com.af.security.service;

import com.af.security.constants.Constants;
import com.af.security.util.JwtTokenUtils;
import com.af.system.mapper.UserRoleMapper;
import com.af.system.service.MenuService;
import com.af.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * 自定义权限校验
 * @author AF
 * @date 2021/5/16 22:34
 */
@Service("ps")
public class PermissionService {


    @Autowired
    private JwtTokenUtils jwtTokenUtils;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private MenuService menuService;

    @Autowired
    private HttpServletRequest request;

    /**
     * 判断用户是否具有该权限
     * @param permission
     * @return true if hasPerm
     */
    public boolean hasPerm(String permission) {
        if (null == permission || "".equals(permission)) {
            return false;
        }
        String token = jwtTokenUtils.getToken(request);
        if (isAdmin(token)) {
            return true;
        }

        long id = jwtTokenUtils.getId(token);
        List<String> perms = menuService.selectPermByUserId(id);
        return perms.contains(permission);
    }

    /**
     * 判断是否含有管理员角色
     * @param token
     * @return
     */
    private boolean isAdmin(String token) {
        List<Long> roles = userRoleMapper.selectUserRoleByUserId(jwtTokenUtils.getId(token));
        return roles.contains(1L);
    }
}
