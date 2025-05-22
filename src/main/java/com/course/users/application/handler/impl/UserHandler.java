package com.course.users.application.handler.impl;

import com.course.users.application.dto.request.UserClientRequest;
import com.course.users.application.dto.request.UserEmployeeRequest;
import com.course.users.application.dto.request.UserRequest;
import com.course.users.application.dto.response.UserResponse;
import com.course.users.application.handler.IUserHandler;
import com.course.users.application.mapper.RoleDtoMapper;
import com.course.users.application.mapper.request.IUserClientRequestMapper;
import com.course.users.application.mapper.request.IUserEmployeeRequestMapper;
import com.course.users.application.mapper.request.UserRequestMapper;
import com.course.users.application.mapper.response.UserResponseMapper;
import com.course.users.domain.api.IRoleServicePort;
import com.course.users.domain.api.IUserServicePort;
import com.course.users.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
public class UserHandler implements IUserHandler {


    private final IUserServicePort userServicePort;
    private final IRoleServicePort roleServicePort;
    private final UserRequestMapper userRequestMapper;
    private final IUserEmployeeRequestMapper userEmployeeRequestMapper;
    private final IUserClientRequestMapper userClientRequestMapper;
    private final UserResponseMapper userResponseMapper;
    private final RoleDtoMapper roleDtoMapper;

    @Override
    public void saveUser(UserRequest userRequest) {
        userServicePort.saveUser(userRequestMapper.toUser(userRequest));
    }

    @Override
    public void saveUserEmployee(UserEmployeeRequest userEmployeeRequest) {
        userServicePort.saveEmployee( userEmployeeRequestMapper.toUser(userEmployeeRequest));
    }

    @Override
    public void saveUserClient(UserClientRequest userClientRequest) {
        userServicePort.saveClient(userClientRequestMapper.toUser(userClientRequest));
    }

    @Override
    public UserResponse getUserById(Long id) {
        User user = userServicePort.getUserById(id);
        return userResponseMapper.toResponse(user, roleDtoMapper.toDto(roleServicePort.getRoleById(user.getIdRole())) );

    }

    @Override
    public List<UserResponse> getAllUsers() {
        return userResponseMapper.toResponseList(userServicePort.getAllUsers(), roleServicePort.getAllRoles());
    }

    @Override
    public void deleteUserById(Long id) {
        userServicePort.deleteUserById(id);
    }
}
