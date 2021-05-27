package com.af.system.mapper;

import com.af.system.entity.RoleMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author AF
 * @date 2021/5/25 22:07
 */
@Mapper
@Repository
public interface RoleMenuMapper {


    /**
     * 批量插入角色权限信息
     * @param roleMenuList
     * @return
     */
    int batchRoleMenu(List<RoleMenu> roleMenuList);

    /**
     * 删除角色权限信息
     * @param roleId
     * @return
     */
    int deleteRoleMenuByRoleId(@Param("roleId") long roleId);
}
