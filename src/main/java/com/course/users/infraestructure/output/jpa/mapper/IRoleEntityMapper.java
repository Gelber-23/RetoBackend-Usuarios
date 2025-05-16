package com.course.users.infraestructure.output.jpa.mapper;


import com.course.users.domain.model.Role;
import com.course.users.infraestructure.output.jpa.entity.RoleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE )
public interface IRoleEntityMapper {

    RoleEntity toEntity(Role role);

    Role toRoleModel(RoleEntity roleEntity);

    List<Role> toRoleModelList(List<RoleEntity> rolEntityList);
}
