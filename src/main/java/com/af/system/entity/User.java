package com.af.system.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.util.Date;
import java.util.List;

/**
 * @author AF
 * @date 2021/5/12 23:00
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private Long userId;

    @Size(min = 6, max = 14, message = "用户名必须在6-14位")
    private String username;

    @Size(min = 6, max = 14, message = "密码必须在6-14位")
    private String password;

    @NotBlank(message = "请输入昵称")
    private String nickname;

    @Email(message = "邮箱格式不正确")
    @NotBlank(message = "请输入邮箱")
    private String userEmail;

    @Size(min = 11, max = 11, message = "手机号必须为11位")
    private String userPhone;

    private Integer userStatus;

    @NotNull(message = "请分配部门")
    private Long deptId;

    private Dept dept;

    @NotEmpty(message = "请分配角色")
    private Long[] roleIdList;

    private List<Role> roleList;

    private Date lastLogin;


}
