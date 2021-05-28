package com.af.system.controller;

import com.af.security.enums.ErrorCode;
import com.af.security.modal.ResponseVO;
import com.af.security.util.JwtTokenUtils;
import com.af.security.util.ResponseUtils;
import com.af.system.annotation.DataScope;
import com.af.system.entity.User;
import com.af.system.service.RoleService;
import com.af.system.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author AF
 * @date 2021/5/20 17:14
 */
@Api(tags = "用户接口")
@RestController
@RequestMapping("/sys/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenUtils jwtTokenUtils;

    /**
     * 查询用户列表
     * @param user
     * @return
     */
    @ApiOperation("查询用户列表")
    @PreAuthorize("@ps.hasPerm('sys:user:query')")
    @GetMapping("/list")
    public ResponseVO<Object> selectUserList(User user) {
        return ResponseUtils.success(userService.selectUserList(user));
    }

    /**
     * 添加用户
     * @param user
     * @return
     */
    @ApiOperation("添加用户")
    @PreAuthorize("@ps.hasPerm('sys:user:add')")
    @PostMapping
    public ResponseVO<Object> createAnUser(@Validated @RequestBody User user) {
        if (userService.checkUsername(user.getUsername())) {
            return ResponseUtils.error(ErrorCode.USER_EXIST);
        }
        if (userService.checkEmail(user.getUserEmail())) {
            return ResponseUtils.error(ErrorCode.EMAIL_EXIST);
        }
        if (userService.checkPhone(user.getUserPhone())) {
            return ResponseUtils.error(ErrorCode.PHONE_EXIST);
        }
        return ResponseUtils.success(userService.createAnUser(user));
    }

    /**
     * 修改用户
     * @param user
     * @return
     */
    @ApiOperation("修改用户")
    @PreAuthorize("@ps.hasPerm('sys:user:edit')")
    @PutMapping
    public ResponseVO<Object> updateUser(@RequestBody User user) {
        userService.checkUserAllowed(user);
        if (null != user.getUserEmail() && userService.checkEmail(user.getUserEmail())) {
            return ResponseUtils.error(ErrorCode.EMAIL_EXIST);
        }
        if (null != user.getUserPhone() && userService.checkPhone(user.getUserPhone())) {
            return ResponseUtils.error(ErrorCode.PHONE_EXIST);
        }
        return ResponseUtils.success(userService.updateUser(user));
    }

    /**
     * 删除用户
     * @param userId
     * @return
     */
    @ApiOperation("删除用户")
    @PreAuthorize("@ps.hasPerm('sys:user:remove')")
    @DeleteMapping("/{userId}")
    public ResponseVO<Object> removeUser(@PathVariable("userId") long userId) {
        userService.checkUserAllowed(userService.selectUserById(userId));
        return ResponseUtils.success(userService.removeUser(userId));
    }

    /**
     * 查询个人信息
     * @param request
     * @return
     */
    @ApiOperation("查询个人信息")
    @GetMapping("/space")
    public ResponseVO<Object> getUserInfo(HttpServletRequest request) {
        long userId = jwtTokenUtils.getId(jwtTokenUtils.getToken(request));
        return ResponseUtils.success(userService.selectUserById(userId));
    }
}
