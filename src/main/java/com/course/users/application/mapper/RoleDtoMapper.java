package com.course.users.application.mapper;

import com.course.users.application.dto.RoleDto;

import com.course.users.domain.model.Role;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface RoleDtoMapper {
    RoleDto toDto(Role role);
}
