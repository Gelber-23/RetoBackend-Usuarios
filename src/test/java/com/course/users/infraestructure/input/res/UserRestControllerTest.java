package com.course.users.infraestructure.input.res;

import com.course.users.application.dto.request.UserClientRequest;
import com.course.users.application.dto.request.UserEmployeeRequest;
import com.course.users.application.dto.request.UserRequest;
import com.course.users.application.dto.response.UserResponse;
import com.course.users.application.handler.IUserHandler;
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
class UserRestControllerTest {
    @Mock
    IUserHandler userHandler;
    @InjectMocks
    UserRestController controller;


    @Test
    void saveUser_shouldCallHandlerAndReturnCreated() {
        UserRequest req = new UserRequest();
        ResponseEntity<Void> response = controller.saveUser(req);

        verify(userHandler).saveUser(req);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void saveUserEmployee_shouldCallHandlerAndReturnCreated() {
        UserEmployeeRequest req = new UserEmployeeRequest();
        ResponseEntity<Void> response = controller.saveUserEmployee(req);

        verify(userHandler).saveUserEmployee(req);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void saveUserClient_shouldCallHandlerAndReturnCreated() {
        UserClientRequest req = new UserClientRequest();
        ResponseEntity<Void> response = controller.saveUserClient(req);

        verify(userHandler).saveUserClient(req);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void getUserById_shouldReturnOkAndUserResponse() {
        Long id = 5L;
        UserResponse expected = new UserResponse();
        when(userHandler.getUserById(id)).thenReturn(expected);

        ResponseEntity<UserResponse> response = controller.getUserById(id);

        verify(userHandler).getUserById(id);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertSame(expected, response.getBody());
    }

    @Test
    void getAllUsers_shouldReturnOkAndList() {
        List<UserResponse> list = Arrays.asList(new UserResponse(), new UserResponse());
        when(userHandler.getAllUsers()).thenReturn(list);

        ResponseEntity<List<UserResponse>> response = controller.getAllUsers();

        verify(userHandler).getAllUsers();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertSame(list, response.getBody());
    }

    @Test
    void deleteUserById_shouldCallHandlerAndReturnOk() {
        Long id = 7L;
        ResponseEntity<Void> response = controller.deleteUserById(id);

        verify(userHandler).deleteUserById(id);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNull(response.getBody());
    }
}