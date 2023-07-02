package com.shopme.admin.security;

import com.shopme.common.entity.RoleApp;
import com.shopme.common.entity.UserApp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class MyUserDetails implements UserDetails {

    private UserApp userApp;
    public MyUserDetails(UserApp userApp){
        this.userApp = userApp;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<RoleApp> roles = userApp.getRoles();
        List<GrantedAuthority> authorityList = new ArrayList<>();
        for (RoleApp role: roles) {
            authorityList.add(new SimpleGrantedAuthority(role.getName()));
        }
        return authorityList;
    }

    @Override
    public String getPassword() {
        return userApp.getPassword();
    }

    @Override
    public String getUsername() {
        return userApp.getEmail();
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
        return userApp.getEnabled();
    }
    public String getFullName(){
        return userApp.getFullName();
    }
}
