package com.project.management.auth.security.service;

import com.project.management.auth.dto.Permission;
import com.project.management.user.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Implementación personalizada de UserDetails utilizada para representar los detalles del usuario autenticado.
 */
public class UserDetailsImpl implements UserDetails {

    private UUID userId;
    private String name;
    private String password;
    private String profileImage;
    private Collection<? extends GrantedAuthority> authorities;

    public UserDetailsImpl(UUID userId, String name, String password, String profileImage, Collection<? extends GrantedAuthority> authorities) {
        this.userId = userId;
        this.name = name;
        this.password = password;
        this.profileImage = profileImage;
        this.authorities = authorities;
    }

    /**
     * Construye una instancia de UserDetailsImpl a partir de un objeto User y una lista de Permission.
     *
     * @param user        El objeto User.
     * @param permissions La lista de Permission.
     * @return Una instancia de UserDetailsImpl construida a partir de los datos proporcionados.
     */
    public static UserDetailsImpl build(User user, List<Permission> permissions) {
        List<GrantedAuthority> authorities = permissions.stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getName()))
                .collect(Collectors.toList());

        return new UserDetailsImpl(
                user.getUserId(),
                user.getName(),
                user.getPassword(),
                user.getProfileImage(),
                authorities
        );
    }

    /**
     * Obtiene el ID del usuario.
     *
     * @return El ID del usuario.
     */
    public UUID getUserId() {
        return userId;
    }

    /**
     * Obtiene la imagen de perfil del usuario.
     *
     * @return La imagen de perfil del usuario.
     */
    public String getProfileImage() {
        return profileImage;
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
        return name;
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
