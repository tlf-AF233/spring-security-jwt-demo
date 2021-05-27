package com.af.system.controller;

import com.af.security.enums.ErrorCode;
import com.af.security.modal.ResponseVO;
import com.af.security.util.ResponseUtils;
import com.af.system.entity.Role;
import com.af.system.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author AF
 * @date 2021/5/25 21:59
 */
@Api(tags = "角色接口")
@RestController
@RequestMapping("/sys/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    /**
     * 查询角色列表
     * @param role
     * @return
     */
    @ApiOperation("查询角色列表")
    @PreAuthorize("@ps.hasPerm('sys:role:query')")
    @GetMapping("/list")
    public ResponseVO<Object> selectRoleList(Role role) {
        return ResponseUtils.success(roleService.selectRoleList(role));
    }

    /**
     * 创建角色
     * @param role
     * @return
     */
    @ApiOperation("创建角色")
    @PreAuthorize("@ps.hasPerm('sys:role:add')")
    @PostMapping
    public ResponseVO<Object> createRole(@Validated @RequestBody Role role) {
        if (roleService.checkRoleCode(role.getRoleCode())) {
            return ResponseUtils.error(ErrorCode.ROLE_EDIT_FAILED, "角色代码重复");
        }
        if (roleService.checkRoleName(role.getRoleName())) {
            return ResponseUtils.error(ErrorCode.ROLE_EDIT_FAILED, "角色名称重复");
        }

        return ResponseUtils.success(roleService.createRole(role));
    }

    /**
     * 更新角色
     * @param role
     * @return
     */
    @ApiOperation("更新角色")
    @PreAuthorize("@ps.hasPerm('sys:role:edit')")
    @PutMapping
    public ResponseVO<Object> updateRole(@RequestBody Role role) {
        if (role.getRoleId() == 1L) {
            return ResponseUtils.error(ErrorCode.NOT_ALLOWED, "不能操作超级管理员角色");
        }
        if (role.getRoleCode() != null && roleService.checkRoleCode(role.getRoleCode())) {
            return ResponseUtils.error(ErrorCode.ROLE_EDIT_FAILED, "角色代码重复");
        }
        if (role.getRoleName() != null && roleService.checkRoleName(role.getRoleName())) {
            return ResponseUtils.error(ErrorCode.ROLE_EDIT_FAILED, "角色名称重复");
        }
        return ResponseUtils.success(roleService.updateRole(role));
    }

    /**
     * 删除角色
     * @param roleId
     * @return
     */
    @ApiOperation("删除角色")
    @PreAuthorize("@ps.hasPerm('sys:role:remove')")
    @DeleteMapping("/{roleId}")
    public ResponseVO<Object> deleteRole(@PathVariable("roleId") long roleId) {
        if (roleId == 1L) {
            return ResponseUtils.error(ErrorCode.NOT_ALLOWED, "不能操作超级管理员角色");
        }
        if (roleService.checkUser(roleId)) {
            return ResponseUtils.error(ErrorCode.ROLE_EDIT_FAILED, "该角色还存在用户，无法删除");
        }
        return ResponseUtils.success(roleService.deleteRole(roleId));
    }
}
