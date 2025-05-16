package com.course.users.application.mapper.response;


import com.course.users.application.dto.response.RoleResponse;
import com.course.users.application.mapper.RoleDtoMapper;
import com.course.users.domain.model.Role;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;


@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        uses = {RoleDtoMapper.class} )
public interface RoleResponseMapper {

    RoleResponse toResponse(Role role);
    List<RoleResponse> toResponseList(List<Role> roleModelLList);

}
