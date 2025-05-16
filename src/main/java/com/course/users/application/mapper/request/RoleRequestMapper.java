package com.course.users.application.mapper.request;

import com.course.users.application.dto.request.RoleRequest;
import com.course.users.domain.model.Role;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface RoleRequestMapper {

    Role toRol(RoleRequest roleRequest);
}
