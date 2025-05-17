package com.course.users.application.handler.impl;

import com.course.users.application.dto.request.RoleRequest;
import com.course.users.application.dto.response.RoleResponse;
import com.course.users.application.mapper.request.RoleRequestMapper;
import com.course.users.application.mapper.response.RoleResponseMapper;
import com.course.users.domain.api.IRoleServicePort;
import com.course.users.domain.model.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class RoleHandlerTest {

    @Mock
    IRoleServicePort roleServicePort;
    @Mock
    RoleRequestMapper roleRequestMapper;
    @Mock
    RoleResponseMapper roleResponseMapper;
    @InjectMocks
    RoleHandler handler;

    private RoleRequest roleRequest;
    private Role role;
    private RoleResponse roleResponse;

    @BeforeEach
    void setUp() {
        roleRequest = new RoleRequest();
        role = new Role();
        roleResponse = new RoleResponse();
    }

    @Test
    void saveRole_success_invokesService() {
        when(roleRequestMapper.toRol(roleRequest)).thenReturn(role);
        handler.saveRole(roleRequest);
        verify(roleRequestMapper).toRol(roleRequest);
        verify(roleServicePort).saveRole(role);
    }

    @Test
    void getRoleById_success_returnsMapped() {
        when(roleServicePort.getRoleById(5)).thenReturn(role);
        when(roleResponseMapper.toResponse(role)).thenReturn(roleResponse);
        RoleResponse result = handler.getRoleById(5);
        assertSame(roleResponse, result);
        verify(roleServicePort).getRoleById(5);
        verify(roleResponseMapper).toResponse(role);
    }

    @Test
    void getAllRoles_success_returnsList() {
        List<Role> roles = List.of(role);
        List<RoleResponse> resps = List.of(roleResponse);
        when(roleServicePort.getAllRoles()).thenReturn(roles);
        when(roleResponseMapper.toResponseList(roles)).thenReturn(resps);
        List<RoleResponse> result = handler.getAllRoles();
        assertEquals(resps, result);
        verify(roleServicePort).getAllRoles();
    }

    @Test
    void deleteRoleById_success_invokesService() {
        handler.deleteRoleById(7);
        verify(roleServicePort).deleteRoleById(7);
    }

    @Test
    void saveRole_mapperThrows_exceptionBubbles() {
        when(roleRequestMapper.toRol(roleRequest)).thenThrow(new IllegalStateException("err"));
        assertThrows(IllegalStateException.class, () -> handler.saveRole(roleRequest));
    }
}