package com.course.users.application.mapper.response;


import com.course.users.application.dto.response.RoleResponse;
import com.course.users.domain.model.Role;

import java.util.List;


public interface RoleResponseMapper {

    RoleResponse toResponse(Role role);
    List<RoleResponse> toResponseList(List<Role> roleModelLList);

}
