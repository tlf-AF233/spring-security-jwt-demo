package com.af.system.mapper;

import com.af.system.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author AF
 * @date 2021/5/13 16:01
 */
@Repository
@Mapper
public interface UserMapper {

    /**
     * 根据用户名查找用户
     * @param username 用户名
     * @return user 对象
     */
    User selectUserByUsername(@Param("username") String username);

    /**
     * 根据 Id 查找用户
     * @param userId
     * @return
     */
    User selectUserById(@Param("userId") long userId);

    /**
     * 查询用户列表
     * @param user
     * @return
     */
    List<User> selectUserList(User user);

    /**
     * 设置最后登录时间
     * @param username
     * @return
     */
    int setLoginTime(@Param("username") String username);

    /**
     * 新增用户
     * @param user
     * @return
     */
    int createAnUser(User user);

    /**
     * 删除用户
     * @param userId
     * @return
     */
    int removeUser(@Param("userId") long userId);

    /**
     * 修改用户
     * @param user
     * @return
     */
    int updateUser(User user);

    /**
     * 检查用户名是否重复
     * @param username
     * @return 1 if 重复
     */
    int checkUsername(String username);

    /**
     * 检查邮箱是否重复
     * @param userEmail
     * @return 1 if 重复
     */
    int checkEmail(String userEmail);

    /**
     * 检查电话号是否重复
     * @param userPhone
     * @return 1 if 重复
     */
    int checkPhone(String userPhone);
}
