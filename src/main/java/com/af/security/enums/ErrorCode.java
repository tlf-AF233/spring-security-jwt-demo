package com.af.security.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 错误码枚举类
 * @author AF
 * @date 2021/5/13 19:38
 */
@Getter
@AllArgsConstructor
public enum ErrorCode implements StatusCode{

    USER_NOT_FOUND(1001, "用户不存在"),
    USER_INVALID(1002, "用户名或者密码错误"),
    USER_UNABLE(1003, "用户不可用"),
    TOKEN_EXPIRATION(1004, "令牌过期"),
    USER_EXIST(1005, "用户名已经存在"),
    EMAIL_EXIST(1006, "邮箱已经注册"),
    PHONE_EXIST(1007, "手机号已经注册"),
    DEPT_DELETED_FAILED(1008, "该部门无法被删除"),
    MENU_EDIT_FAILED(1009, "菜单修改失败"),
    ROLE_EDIT_FAILED(1010, "角色操作失败"),
    ARGUMENT_INVALID(1110, "参数校验失败"),
    NOT_ALLOWED(3001, "没有操作权限"),
    UNAUTHORIZED(4001, "未授权"),
    FORBIDDEN(4003, "权限不足");

    private final Integer code;

    private final String msg;
}
