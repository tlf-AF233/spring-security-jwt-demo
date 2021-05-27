package com.af.system.mapper;

import com.af.system.entity.RoleDept;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author AF
 * @date 2021/5/25 22:17
 */
@Mapper
@Repository
public interface RoleDeptMapper {

    /**
     * 批量插入角色部门信息
     * @param roleDeptList
     * @return
     */
    int batchRoleDept(List<RoleDept> roleDeptList);

    /**
     * 根据角色 id 删除角色部门信息
     * @param roleId
     * @return
     */
    int deleteRoleDeptByRoleId(@Param("roleId") long roleId);
}
