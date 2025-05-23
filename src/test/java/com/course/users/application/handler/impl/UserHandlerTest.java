package com.course.users.application.handler.impl;


import com.course.users.application.dto.request.UserClientRequest;
import com.course.users.application.dto.request.UserEmployeeRequest;
import com.course.users.application.dto.request.UserRequest;
import com.course.users.application.dto.response.UserResponse;
import com.course.users.application.mapper.request.IUserClientRequestMapper;
import com.course.users.application.mapper.request.IUserEmployeeRequestMapper;
import com.course.users.application.mapper.request.UserRequestMapper;
import com.course.users.application.mapper.response.UserResponseMapper;
import com.course.users.domain.api.IRoleServicePort;
import com.course.users.domain.api.IUserServicePort;
import com.course.users.domain.model.Role;
import com.course.users.domain.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class UserHandlerTest {

    @Mock
    IUserServicePort userServicePort;
    @Mock
    IRoleServicePort roleServicePort;
    @Mock
    UserRequestMapper userRequestMapper;
    @Mock
    IUserEmployeeRequestMapper userEmployeeRequestMapper;
    @Mock
    IUserClientRequestMapper userClientRequestMapper;
    @Mock
    UserResponseMapper userResponseMapper;

    @InjectMocks
    UserHandler userHandler;


    @Test
    void saveUser_callsServiceSaveUser() {
        UserRequest req = mock(UserRequest.class);
        User user = mock(User.class);
        when(userRequestMapper.toUser(req)).thenReturn(user);

        userHandler.saveUser(req);

        verify(userServicePort).saveUser(user);
    }

    @Test
    void saveUserEmployee_callsServiceSaveEmployee() {
        UserEmployeeRequest req = mock(UserEmployeeRequest.class);
        User user = mock(User.class);
        when(userEmployeeRequestMapper.toUser(req)).thenReturn(user);

        userHandler.saveUserEmployee(req);

        verify(userServicePort).saveEmployee(user);
    }

    @Test
    void saveUserClient_callsServiceSaveClient() {
        UserClientRequest req = mock(UserClientRequest.class);
        User user = mock(User.class);
        when(userClientRequestMapper.toUser(req)).thenReturn(user);

        userHandler.saveUserClient(req);

        verify(userServicePort).saveClient(user);
    }

    @Test
    void getUserById_returnsMappedResponse() {
        Long id = 1L;
        User user = mock(User.class);
        when(user.getIdRole()).thenReturn(2);
        UserResponse response = mock(UserResponse.class);

        when(userServicePort.getUserById(id)).thenReturn(user);
        when(roleServicePort.getRoleById(2)).thenReturn(mock(Role.class)); // role object
        when(userResponseMapper.toResponse(any(), any())).thenReturn(response);

        UserResponse result = userHandler.getUserById(id);

        verify(userServicePort).getUserById(id);
        verify(roleServicePort).getRoleById(2);
        verify(userResponseMapper).toResponse(user, roleServicePort.getRoleById(2));
        assertSame(response, result);
    }

    @Test
    void getAllUsers_returnsMappedResponseList() {
        List<User> users = Collections.singletonList(mock(User.class));
        List<Role> roles = Collections.singletonList(mock(Role.class));
        List<UserResponse> responseList = Collections.singletonList(mock(UserResponse.class));

        when(userServicePort.getAllUsers()).thenReturn(users);
        when(roleServicePort.getAllRoles()).thenReturn(roles);
        when(userResponseMapper.toResponseList(users, roles)).thenReturn(responseList);

        List<UserResponse> result = userHandler.getAllUsers();

        verify(userServicePort).getAllUsers();
        verify(roleServicePort).getAllRoles();
        verify(userResponseMapper).toResponseList(users, roles);
        assertSame(responseList, result);
    }

    @Test
    void deleteUserById_callsServiceDelete() {
        Long id = 1L;

        userHandler.deleteUserById(id);

        verify(userServicePort).deleteUserById(id);
    }
  
}