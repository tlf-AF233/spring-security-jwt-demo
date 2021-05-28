package com.af.system.service.impl;

import com.af.system.entity.Role;
import com.af.system.entity.RoleDept;
import com.af.system.entity.RoleMenu;
import com.af.system.entity.User;
import com.af.system.mapper.RoleDeptMapper;
import com.af.system.mapper.RoleMapper;
import com.af.system.mapper.RoleMenuMapper;
import com.af.system.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author AF
 * @date 2021/5/25 21:58
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private RoleMenuMapper roleMenuMapper;

    @Autowired
    private RoleDeptMapper roleDeptMapper;

    /**
     * 查询角色列表
     * @param role
     * @return
     */
    @Override
    public List<Role> selectRoleList(Role role) {
        return roleMapper.selectRoleList(role);
    }

    /**
     * 创建角色
     * @param role
     * @return
     */
    @Transactional
    @Override
    public int createRole(Role role) {
        roleMapper.createRole(role);
        if (role.getDeptId() != null) {
            createRoleDept(role);
        }
        return createRoleMenu(role);
    }

    /**
     * 修改角色
     * @param role
     * @return
     */
    @Transactional
    @Override
    public int updateRole(Role role) {
        if (role.getDeptId() != null) {
            roleDeptMapper.deleteRoleDeptByRoleId(role.getRoleId());
            createRoleDept(role);
        }
        if (role.getMenuId() != null) {
            roleMenuMapper.deleteRoleMenuByRoleId(role.getRoleId());
            createRoleMenu(role);
        }
        return roleMapper.updateRole(role);
    }

    /**
     * 删除角色
     * @param roleId
     * @return
     */
    @Transactional
    @Override
    public int deleteRole(long roleId) {
        roleDeptMapper.deleteRoleDeptByRoleId(roleId);
        roleMenuMapper.deleteRoleMenuByRoleId(roleId);
        return roleMapper.deleteRole(roleId);
    }

    /**
     * 检查角色名称是否重复
     * @param roleName
     * @return
     */
    @Override
    public boolean checkRoleName(String roleName) {
        return roleMapper.checkRoleName(roleName) == 1;
    }

    /**
     * 检查角色代码是否重复
     * @param roleCode
     * @return
     */
    @Override
    public boolean checkRoleCode(String roleCode) {
        return roleMapper.checkRoleCode(roleCode) == 1;
    }

    /**
     * 检查角色是否还存在用户
     * @param roleId
     * @return
     */
    @Override
    public boolean checkUser(long roleId) {
        return roleMapper.checkUser(roleId) == 1;
    }

    /**
     * 创建角色菜单信息
     * @param role
     * @return
     */
    private int createRoleMenu(Role role) {
        List<RoleMenu> roleMenus = new ArrayList<>();
        Arrays.stream(role.getMenuId())
                .forEach(id -> roleMenus.add(new RoleMenu(role.getRoleId(), id)));
        return roleMenuMapper.batchRoleMenu(roleMenus);
    }

    /**
     * 创建角色部门信息
     * @param role
     * @return
     */
    private int createRoleDept(Role role) {
        List<RoleDept> roleDeptList = new ArrayList<>();
        Arrays.stream(role.getDeptId())
                .forEach(id -> roleDeptList.add(new RoleDept(role.getRoleId(), id)));
        return roleDeptMapper.batchRoleDept(roleDeptList);
    }

}
