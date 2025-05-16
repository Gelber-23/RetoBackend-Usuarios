package com.course.users.application.handler;

import com.course.users.application.dto.request.UserRequest;
import com.course.users.application.dto.response.UserResponse;


import java.util.List;

public interface IUserHandler {

    void saveUser(UserRequest userRequest);

    UserResponse getUserById(Long id);

    List<UserResponse> getAllUsers();

    void deleteUserById(Long id);
}
