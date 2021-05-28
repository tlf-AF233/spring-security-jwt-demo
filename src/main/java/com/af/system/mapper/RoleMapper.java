package com.af.system.mapper;

import com.af.system.entity.Role;
import com.af.system.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author AF
 * @date 2021/5/25 20:45
 */
@Repository
@Mapper
public interface RoleMapper {

    /**
     * 查询角色信息
     * @param role
     * @return
     */
    List<Role> selectRoleList(Role role);

    /**
     * 新增角色
     * @param role
     * @return
     */
    int createRole(Role role);

    /**
     * 检查角色名称是否重复
     * @param roleName
     * @return
     */
    int checkRoleName(@Param("roleName") String roleName);

    /**
     * 检查角色代码是否重复
     * @param roleCode
     * @return
     */
    int checkRoleCode(@Param("roleCode") String roleCode);

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
    int deleteRole(@Param("roleId") long roleId);

    /**
     * 检查角色是否还存在用户
     * @param roleId
     * @return
     */
    int checkUser(@Param("roleId") long roleId);

}
