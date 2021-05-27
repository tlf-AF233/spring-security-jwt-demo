package com.af.system.service;

import com.af.system.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author AF
 * @date 2021/5/13 16:07
 */
public interface UserService {

    /**
     * 根据用户名查找用户
     * @param username 用户名
     * @return user 对象
     */
    User selectUserByUsername(String username);

    /**
     * 根据 Id 查找用户
     * @param userId
     * @return
     */
    User selectUserById(long userId);

    /**
     * 查询用户列表
     * @param user
     * @return
     */
    List<User> selectUserList(User user);

    /**
     * 新增用户
     * @param user
     * @return
     */
    int createAnUser(User user);

    /**
     * 设置最后登录时间
     * @param username
     * @return
     */
    int setLoginTime(String username);

    /**
     * 删除用户
     * @param userId
     * @return
     */
    int removeUser(long userId);

    /**
     * 修改用户
     * @param user
     * @return
     */
    int updateUser(User user);

    /**
     * 检查对该用户能否进行操作
     * @param user
     */
    void checkUserAllowed(User user);

    /**
     * 检查用户名是否重复
     * @param username
     * @return
     */
    boolean checkUsername(String username);

    /**
     * 检查邮箱是否重复
     * @param userEmail
     * @return
     */
    boolean checkEmail(String userEmail);

    /**
     * 检查电话号是否重复
     * @param userPhone
     * @return
     */
    boolean checkPhone(String userPhone);
}
