package com.course.users.application.handler;

import com.course.users.application.dto.request.RoleRequest;
import com.course.users.application.dto.response.RoleResponse;

import java.util.List;

public interface IRoleHandler {

    void saveRole(RoleRequest roleRequest);

    RoleResponse getRoleById(int id);

    List<RoleResponse> getAllRoles();

    void deleteRoleById(int id);
}
