package com.why.transportsecurity.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * @ClassName：User
 * @Description：todo 自定义用户
 * @Author: why
 * @DateTime：2021/12/11 14:36
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AdminLogin implements UserDetails {

    private Integer id;
    private String username;
    private String password;
    private String auths;

    public Integer getId() {
        return id;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(this.auths);
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
        return true;
    }

    public String getAuths() {
        return auths;
    }
}
