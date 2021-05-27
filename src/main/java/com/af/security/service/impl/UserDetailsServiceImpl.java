package com.af.security.service.impl;

import com.af.security.constants.Constants;
import com.af.security.exception.UserNotFoundException;
import com.af.security.exception.UserUnableException;
import com.af.security.modal.LoginUser;
import com.af.system.entity.Role;
import com.af.system.entity.User;
import com.af.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author AF
 * @date 2021/5/13 19:29
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.selectUserByUsername(username);
        if (null == user) {
            throw new UserNotFoundException("用户名 " + username + " 不存在");
        }
        if (Constants.USER_UNABLE.equals(user.getUserStatus())) {
            throw new UserUnableException(username + " 暂时不可用，请联系管理员！");
        }
        return createLoginUser(user);
    }

    /**
     * 创建 userDetails 对象
     * @param user
     * @return
     */
    private UserDetails createLoginUser(User user) {
        // 将角色对象转换为 SimpleGrantedAuthority 对象
        List<String> roles = user.getRoleList().stream()
                .map(Role::getRoleCode)
                .collect(Collectors.toList());
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        roles.forEach(role -> authorities.add(new SimpleGrantedAuthority(role)));
        return new LoginUser(user, authorities);
    }
}
