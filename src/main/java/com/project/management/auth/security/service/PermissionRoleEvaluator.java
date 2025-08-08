package com.project.management.auth.security.service;

import jakarta.servlet.http.HttpSession;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * Evaluador de permisos basado en roles.
 */
@Component
public class PermissionRoleEvaluator implements PermissionEvaluator {

    private final HttpSession httpSession;

    public PermissionRoleEvaluator(HttpSession httpSession) {
        this.httpSession = httpSession;
    }

    /**
     * Verifica si el usuario autenticado tiene el permiso especificado.
     *
     * @param authentication     La información de autenticación del usuario.
     * @param targetDomainObject El objeto objetivo.
     * @param permission         El permiso a verificar.
     * @return `true` si el usuario tiene el permiso, de lo contrario, `false`.
     */
    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        httpSession.setAttribute("permission",permission);
        if ((authentication == null) || (targetDomainObject == null) || !(permission instanceof String permissionName)){
            return false;
        }
        return authentication.getAuthorities().stream().anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(permissionName));
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        return false;
    }
}
