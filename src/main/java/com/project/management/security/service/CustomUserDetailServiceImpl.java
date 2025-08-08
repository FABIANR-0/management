package com.project.management.security.service;

import com.project.management.permission.dto.PermissionResponse;
import com.project.management.user.entity.User;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
public class CustomUserDetailServiceImpl implements UserDetails {

    private UUID userId;

    private String userName;

    private String password;

    private String imgProfile;

    private Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetailServiceImpl(UUID userId, String userName, String password, String imgProfile, Collection<? extends GrantedAuthority> authorities) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.imgProfile = imgProfile;
        this.authorities = authorities;
    }

    public static CustomUserDetailServiceImpl build(User user, List<PermissionResponse> permissions){
        List<GrantedAuthority> grantedAuthorities = permissions.stream().map(permission -> new SimpleGrantedAuthority(permission.getPermissionName())).collect(Collectors.toList());
        return new CustomUserDetailServiceImpl(
                user.getUserId(),
                user.getUserName(),
                user.getPassword(),
                user.getProfileImage(),
                grantedAuthorities
        );
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
        return userName;
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
}
