package com.project.management.security.service;

import jakarta.servlet.http.HttpSession;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class PermissionRoleEvaluatorImpl implements PermissionEvaluator {

    private final HttpSession httpSession;

    public PermissionRoleEvaluatorImpl(HttpSession httpSession) {
        this.httpSession = httpSession;
    }


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
