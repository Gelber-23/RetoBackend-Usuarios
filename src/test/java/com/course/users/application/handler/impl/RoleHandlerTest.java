package com.course.users.application.handler.impl;

import com.course.users.application.dto.request.RoleRequest;
import com.course.users.application.dto.response.RoleResponse;
import com.course.users.application.mapper.request.RoleRequestMapper;
import com.course.users.application.mapper.response.RoleResponseMapper;
import com.course.users.domain.api.IRoleServicePort;
import com.course.users.domain.model.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RoleHandlerTest {

    @Mock
    IRoleServicePort roleServicePort;
    @Mock
    RoleRequestMapper roleRequestMapper;
    @Mock
    RoleResponseMapper roleResponseMapper;

    @InjectMocks
    RoleHandler roleHandler;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveRole_callsServiceSaveRole() {
        RoleRequest req = mock(RoleRequest.class);
        Role rol = mock(Role.class);
        when(roleRequestMapper.toRol(req)).thenReturn(rol);

        roleHandler.saveRole(req);

        verify(roleRequestMapper).toRol(req);
        verify(roleServicePort).saveRole(rol);
    }

    @Test
    void getRoleById_returnsMappedResponse() {
        int id = 42;
        Role role = mock(Role.class);
        RoleResponse resp = mock(RoleResponse.class);

        when(roleServicePort.getRoleById(id)).thenReturn(role);
        when(roleResponseMapper.toResponse(role)).thenReturn(resp);

        RoleResponse result = roleHandler.getRoleById(id);

        verify(roleServicePort).getRoleById(id);
        verify(roleResponseMapper).toResponse(role);
        assertSame(resp, result);
    }

    @Test
    void getAllRoles_returnsMappedList() {
        List<Role> roles = Collections.singletonList(mock(Role.class));
        List<RoleResponse> resps = Collections.singletonList(mock(RoleResponse.class));

        when(roleServicePort.getAllRoles()).thenReturn(roles);
        when(roleResponseMapper.toResponseList(roles)).thenReturn(resps);

        List<RoleResponse> result = roleHandler.getAllRoles();

        verify(roleServicePort).getAllRoles();
        verify(roleResponseMapper).toResponseList(roles);
        assertSame(resps, result);
    }

    @Test
    void deleteRoleById_callsServiceDelete() {
        int id = 99;

        roleHandler.deleteRoleById(id);

        verify(roleServicePort).deleteRoleById(id);
    }

}