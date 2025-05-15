package com.course.users.domain.spi;

import com.course.users.domain.model.Role;

import java.util.List;

public interface IRolePersistencePort {
    void saveRole(Role role);

    Role getRoleById(int id);

    List<Role> getAllRoles();

    void deleteRoleById(int id);
}
