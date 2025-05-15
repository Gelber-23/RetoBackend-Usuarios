package com.course.users.domain.api;


import com.course.users.domain.model.Role;

import java.util.List;

public interface IRoleServicePort {


    void saveRole(Role role);

    Role getRoleById(int id);

    List<Role> getAllRoles();

    void deleteRoleById(int id);
}
