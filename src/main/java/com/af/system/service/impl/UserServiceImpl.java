package com.af.system.service.impl;

import com.af.security.enums.ErrorCode;
import com.af.security.exception.CustomException;
import com.af.system.annotation.DataScope;
import com.af.system.entity.Role;
import com.af.system.entity.User;
import com.af.system.entity.UserRole;
import com.af.system.mapper.UserMapper;
import com.af.system.mapper.UserRoleMapper;
import com.af.system.service.UserService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author AF
 * @date 2021/5/13 16:07
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;



    /**
     * 根据用户名查找用户
     * @param username 用户名
     * @return user 对象
     */
    @Override
    public User selectUserByUsername(String username) {
        return userMapper.selectUserByUsername(username);
    }

    /**
     * 根据 Id 查找用户
     * @param userId
     * @return
     */
    @Override
    public User selectUserById(long userId) {
        return userMapper.selectUserById(userId);
    }

    /**
     * 查询用户列表
     * @param user
     * @return
     */
    @DataScope(deptAlias = "d", userAlias = "u")
    @Override
    public List<User> selectUserList(User user) {
        return userMapper.selectUserList(user);
    }

    /**
     * 创建一个用户
     * @param user
     * @return
     */
    @Transactional
    @Override
    public int createAnUser(User user) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String password = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(password);
        // 保存该用户的角色信息
        userMapper.createAnUser(user);
        return batchUserRole(user);
    }

    /**
     * 设置最后登录时间
     * @param username
     * @return
     */
    @Override
    public int setLoginTime(String username) {
        return userMapper.setLoginTime(username);
    }

    /**
     * 删除用户信息
     * @param userId
     * @return
     */
    @Transactional
    @Override
    public int removeUser(long userId) {
        userRoleMapper.deleteUserRoleByUserId(userId);
        return userMapper.removeUser(userId);
    }

    /**
     * 更新用户信息
     * @param user
     * @return
     */
    @Transactional
    @Override
    public int updateUser(User user) {
        // 更新用户角色信息
        if (user.getRoleIdList() != null) {
            userRoleMapper.deleteUserRoleByUserId(user.getUserId());
            batchUserRole(user);
        }
        return userMapper.updateUser(user);
    }

    /**
     * 判断该用户是否能被操作
     * @param user
     */
    @Override
    public void checkUserAllowed(User user) {
        long userId = user.getUserId();
        if (userRoleMapper.selectUserRoleByUserId(userId).contains(1L)) {
            throw new CustomException(ErrorCode.NOT_ALLOWED);
        }
    }

    /**
     * 检查用户名是否重复
     * @param username
     * @return
     */
    @Override
    public boolean checkUsername(String username) {
        return userMapper.checkUsername(username) == 1;
    }

    /**
     * 检查邮箱是否重复
     * @param userEmail
     * @return
     */
    @Override
    public boolean checkEmail(String userEmail) {
        return userMapper.checkEmail(userEmail) == 1;
    }

    /**
     * 检查电话号是否重复
     * @param userPhone
     * @return
     */
    @Override
    public boolean checkPhone(String userPhone) {
        return userMapper.checkPhone(userPhone) == 1;
    }

    /**
     * 批量插入用户角色信息
     * @param user
     */
    private int batchUserRole(User user) {
        List<UserRole> list = new ArrayList<>();
        Arrays.stream(user.getRoleIdList())
                .forEach(id -> list.add(new UserRole(user.getUserId(), id)));
        return userRoleMapper.batchUserRole(list);
    }
}
