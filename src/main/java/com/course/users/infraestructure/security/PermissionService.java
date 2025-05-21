package com.course.users.infraestructure.security;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class PermissionService {

    private static final String ROLE_ADMIN= "ROLE_1";
    private static final String ROLE_OWNER= "ROLE_2";

    public boolean isAdmin(Authentication auth) {
        return auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals(ROLE_ADMIN));
    }

    public boolean isAdminOrOwner(Authentication auth) {
        return auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals(ROLE_ADMIN) || a.getAuthority().equals(ROLE_OWNER));
    }
    public boolean isOwner(Authentication auth) {
        return auth.getAuthorities().stream()
                .anyMatch(a ->  a.getAuthority().equals(ROLE_OWNER));
    }
}
