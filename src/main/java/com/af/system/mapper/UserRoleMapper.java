package com.af.system.mapper;

import com.af.system.entity.UserRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author AF
 * @date 2021/5/21 16:19
 */
@Repository
@Mapper
public interface UserRoleMapper {

    /**
     * 批量插入用户角色信息
     * @param userRoleList
     * @return
     */
    int batchUserRole(List<UserRole> userRoleList);

    /**
     * 查询用户角色信息
     * @param userId
     * @return
     */
    List<Long> selectUserRoleByUserId(@Param("userId") long userId);

    /**
     * 删除用户角色信息
     * @param userId
     * @return
     */
    int deleteUserRoleByUserId(@Param("userId") long userId);
}
