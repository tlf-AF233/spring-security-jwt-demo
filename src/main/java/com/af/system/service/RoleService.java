package com.af.system.service;

import com.af.system.entity.Role;
import com.af.system.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author AF
 * @date 2021/5/25 21:58
 */
public interface RoleService {

    /**
     * 查询角色信息
     * @param role
     * @return
     */
    List<Role> selectRoleList(Role role);

    /**
     * 创建角色
     * @param role
     * @return
     */
    int createRole(Role role);

    /**
     * 修改角色
     * @param role
     * @return
     */
    int updateRole(Role role);

    /**
     * 删除角色
     * @param roleId
     * @return
     */
    int deleteRole(long roleId);

    /**
     * 检查角色名称是否重复
     * @param roleName
     * @return
     */
    boolean checkRoleName(String roleName);

    /**
     * 检查角色代码是否重复
     * @param roleCode
     * @return
     */
    boolean checkRoleCode(String roleCode);

    /**
     * 检查角色是否还存在用户
     * @param roleId
     * @return
     */
    boolean checkUser(long roleId);
}
