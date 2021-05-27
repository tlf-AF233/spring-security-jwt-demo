package com.af.security.modal;

import com.af.system.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * UserDetails 用户类
 * @author AF
 * @date 2021/5/12 22:35
 */
public class LoginUser implements UserDetails {

    private Long userId;

    private String username;

    private String password;

    private Boolean isEnabled;

    private Collection<? extends GrantedAuthority> authorities;

    public LoginUser() {
    }

    public LoginUser(User user, Collection<? extends GrantedAuthority> authorities) {
        this.userId = user.getUserId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.isEnabled = user.getUserStatus() == 0;
        this.authorities = authorities;
    }

    public Long getUserId() {
        return userId;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }
}
