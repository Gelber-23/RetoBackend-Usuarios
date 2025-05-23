package com.course.users.infraestructure.input.res;

import com.course.users.application.dto.request.RoleRequest;
import com.course.users.application.dto.response.RoleResponse;
import com.course.users.application.handler.IRoleHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RoleRestControllerTest {
    @Mock
    IRoleHandler roleHandler;
    @InjectMocks
    RoleRestController controller;


    @Test
    void saveRole_shouldCallHandlerAndReturnCreated() {
        RoleRequest req = new RoleRequest();
        ResponseEntity<Void> response = controller.saveRole(req);

        verify(roleHandler).saveRole(req);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void getRoleById_shouldReturnOkAndRoleResponse() {
        int id = 10;
        RoleResponse expected = new RoleResponse();
        when(roleHandler.getRoleById(id)).thenReturn(expected);

        ResponseEntity<RoleResponse> response = controller.getRoleById(id);

        verify(roleHandler).getRoleById(id);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertSame(expected, response.getBody());
    }

    @Test
    void getAllRoles_shouldReturnOkAndList() {
        List<RoleResponse> list = Arrays.asList(new RoleResponse(), new RoleResponse());
        when(roleHandler.getAllRoles()).thenReturn(list);

        ResponseEntity<List<RoleResponse>> response = controller.getAllRoles();

        verify(roleHandler).getAllRoles();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertSame(list, response.getBody());
    }

    @Test
    void deleteRoleById_shouldCallHandlerAndReturnOk() {
        int id = 5;
        ResponseEntity<Void> response = controller.deleteRoleById(id);

        verify(roleHandler).deleteRoleById(id);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNull(response.getBody());
    }
}