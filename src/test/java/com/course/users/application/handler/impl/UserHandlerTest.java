package com.course.users.application.handler.impl;

import com.course.users.application.dto.RoleDto;
import com.course.users.application.dto.request.UserRequest;
import com.course.users.application.dto.response.UserResponse;
import com.course.users.application.mapper.RoleDtoMapper;
import com.course.users.application.mapper.request.UserRequestMapper;
import com.course.users.application.mapper.response.UserResponseMapper;
import com.course.users.domain.api.IRoleServicePort;
import com.course.users.domain.api.IUserServicePort;
import com.course.users.domain.model.Role;
import com.course.users.domain.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserHandlerTest {
    @Mock
    IUserServicePort userServicePort;
    @Mock
    IRoleServicePort roleServicePort;
    @Mock
    UserRequestMapper userRequestMapper;
    @Mock
    UserResponseMapper userResponseMapper;
    @Mock
    RoleDtoMapper roleDtoMapper;
    @Mock
    PasswordEncoder passwordEncoder;
    @InjectMocks
    UserHandler userHandler;

    private UserRequest userRequest;
    private User user;
    private Role role;
    private RoleDto roleDto;
    private UserResponse userResponse;

    @BeforeEach
    void setUp() {
        userRequest = new UserRequest();
        userRequest.setPassword("raw");
        user = new User();
        role = new Role();
        role.setId(1);
        roleDto = new RoleDto();
        roleDto.setName("R");
        userResponse = new UserResponse();
    }

    @Test
    void saveUser_success_invokesServiceWithEncodedPassword() {
        when(passwordEncoder.encode("raw")).thenReturn("enc");
        when(userRequestMapper.toUser(any())).thenReturn(user);
        userHandler.saveUser(userRequest);
        assertEquals("enc", userRequest.getPassword());
        verify(passwordEncoder).encode("raw");
        verify(userRequestMapper).toUser(userRequest);
        verify(userServicePort).saveUser(user);
    }

    @Test
    void getUserById_success_returnsMappedResponse() {
        when(userServicePort.getUserById(2L)).thenReturn(user);
        when(roleServicePort.getRoleById(user.getIdRole())).thenReturn(role);
        when(roleDtoMapper.toDto(role)).thenReturn(roleDto);
        when(userResponseMapper.toResponse(user, roleDto)).thenReturn(userResponse);

        UserResponse result = userHandler.getUserById(2L);
        assertSame(userResponse, result);
        verify(userServicePort).getUserById(2L);
        verify(roleServicePort).getRoleById(user.getIdRole());
        verify(userResponseMapper).toResponse(user, roleDto);
    }

    @Test
    void getAllUsers_success_returnsList() {
        List<User> users = List.of(user);
        List<Role> roles = List.of(role);
        List<UserResponse> userResponseList = List.of(userResponse);
        when(userServicePort.getAllUsers()).thenReturn(users);
        when(roleServicePort.getAllRoles()).thenReturn(roles);
        when(userResponseMapper.toResponseList(users, roles)).thenReturn(userResponseList);

        List<UserResponse> result = userHandler.getAllUsers();
        assertEquals(userResponseList, result);
        verify(userServicePort).getAllUsers();
        verify(roleServicePort).getAllRoles();
    }

    @Test
    void deleteUserById_success_invokesService() {
        userHandler.deleteUserById(3L);
        verify(userServicePort).deleteUserById(3L);
    }

    @Test
    void saveUser_mapperThrows_exceptionBubbles() {
        when(passwordEncoder.encode(any())).thenReturn("enc");
        when(userRequestMapper.toUser(any())).thenThrow(new RuntimeException("fail"));
        assertThrows(RuntimeException.class, () -> userHandler.saveUser(userRequest));
    }
}