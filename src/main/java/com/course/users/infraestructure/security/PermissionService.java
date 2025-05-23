package com.course.users.infraestructure.security;

import com.course.users.domain.utils.constants.ValuesConstants;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class PermissionService {

    private static final String ROLE_ADMIN= ValuesConstants.STRING_ROLE_ADMIN;
    private static final String ROLE_OWNER= ValuesConstants.STRING_ROLE_OWNER;

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
