package com.course.users.domain.usercase;

import com.course.users.domain.api.IRoleServicePort;
import com.course.users.domain.model.Role;
import com.course.users.domain.spi.IRolePersistencePort;

import java.util.List;

public class RoleUseCase implements IRoleServicePort {

    private final IRolePersistencePort rolePersistencePort;

    public RoleUseCase(IRolePersistencePort rolePersistencePort) {
        this.rolePersistencePort = rolePersistencePort;
    }

    @Override
    public void saveRole(Role role) {
        rolePersistencePort.saveRole(role);
    }

    @Override
    public Role getRoleById(int id) {
        return rolePersistencePort.getRoleById(id);
    }

    @Override
    public List<Role> getAllRoles() {
        return rolePersistencePort.getAllRoles();
    }

    @Override
    public void deleteRoleById(int id) {
        rolePersistencePort.deleteRoleById(id);
    }
}
