package com.af.system.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author AF
 * @date 2021/5/12 23:00
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Role extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private Long roleId;

    @NotBlank(message = "请填写角色名")
    private String roleName;

    @NotBlank(message = "请填写角色代码")
    private String roleCode;

    private Integer orderNum;

    private Integer roleStatus;

    private Integer dataScope;

    private String roleDesc;

    private Long[] deptId;

    private Long[] menuId;
}
