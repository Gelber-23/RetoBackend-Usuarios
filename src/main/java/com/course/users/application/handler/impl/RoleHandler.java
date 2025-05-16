package com.course.users.application.handler.impl;

import com.course.users.application.dto.request.RoleRequest;
import com.course.users.application.dto.response.RoleResponse;
import com.course.users.application.handler.IRoleHandler;
import com.course.users.application.mapper.request.RoleRequestMapper;
import com.course.users.application.mapper.response.RoleResponseMapper;
import com.course.users.domain.api.IRoleServicePort;
import com.course.users.domain.model.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
public class RoleHandler implements IRoleHandler {


    private final IRoleServicePort roleServicePort;
    private final RoleRequestMapper roleRequestMapper;
    private final RoleResponseMapper roleResponseMapper;

    @Override
    public void saveRole(RoleRequest roleRequest) {
        Role rol = roleRequestMapper.toRol(roleRequest);
        roleServicePort.saveRole(rol);
    }

    @Override
    public RoleResponse getRoleById(int id) {
        return  roleResponseMapper.toResponse(roleServicePort.getRoleById(id));

    }

    @Override
    public List<RoleResponse> getAllRoles() {
        return roleResponseMapper.toResponseList(roleServicePort.getAllRoles());
    }

    @Override
    public void deleteRoleById(int id) {
        roleServicePort.deleteRoleById(id);
    }
}
