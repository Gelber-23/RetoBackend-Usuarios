package com.curso.usuarios.domain.api;


import com.curso.usuarios.domain.model.Role;

import java.util.List;

public interface IRoleServicePort {


    void saveRole(Role role);

    Role getRoleById(int id);

    List<Role> getAllRoles();

    void deleteRoleById(int id);
}
