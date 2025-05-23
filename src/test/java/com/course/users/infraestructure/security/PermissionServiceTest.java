package com.course.users.infraestructure.security;

import com.course.users.domain.utils.constants.ValuesConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PermissionServiceTest {
    private PermissionService permissionService;

    @BeforeEach
    void setUp() {
        permissionService = new PermissionService();
    }

    private Authentication mockAuthWithRoles(String... roles) {
        Authentication auth = mock(Authentication.class);
        List<GrantedAuthority> authorities = Arrays.stream(roles)
                .map(role -> {
                    GrantedAuthority ga = mock(GrantedAuthority.class);
                    when(ga.getAuthority()).thenReturn(role);
                    return ga;
                })
                .collect(Collectors.toList());


        doReturn(authorities).when(auth).getAuthorities();
        return auth;
    }

    @Test
    void isAdmin_returnsTrue_whenAdminPresent() {
        Authentication auth = mockAuthWithRoles(ValuesConstants.STRING_ROLE_ADMIN);
        assertTrue(permissionService.isAdmin(auth));
    }

    @Test
    void isAdmin_returnsFalse_whenAdminAbsent() {
        Authentication auth = mockAuthWithRoles("ROLE_USER", ValuesConstants.STRING_ROLE_OWNER);
        assertFalse(permissionService.isAdmin(auth));
    }

    @Test
    void isOwner_returnsTrue_whenOwnerPresent() {
        Authentication auth = mockAuthWithRoles(ValuesConstants.STRING_ROLE_OWNER);
        assertTrue(permissionService.isOwner(auth));
    }

    @Test
    void isOwner_returnsFalse_whenOwnerAbsent() {
        Authentication auth = mockAuthWithRoles("ROLE_USER", ValuesConstants.STRING_ROLE_ADMIN);
        assertFalse(permissionService.isOwner(auth));
    }

    @Test
    void isAdminOrOwner_returnsTrue_whenAdminPresent() {
        Authentication auth = mockAuthWithRoles(ValuesConstants.STRING_ROLE_ADMIN);
        assertTrue(permissionService.isAdminOrOwner(auth));
    }

    @Test
    void isAdminOrOwner_returnsTrue_whenOwnerPresent() {
        Authentication auth = mockAuthWithRoles(ValuesConstants.STRING_ROLE_OWNER);
        assertTrue(permissionService.isAdminOrOwner(auth));
    }

    @Test
    void isAdminOrOwner_returnsFalse_whenNeitherPresent() {
        Authentication auth = mockAuthWithRoles("ROLE_USER", "ROLE_GUEST");
        assertFalse(permissionService.isAdminOrOwner(auth));
    }

}