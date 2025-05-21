package com.course.users.application.mapper.request;

import com.course.users.application.dto.request.UserEmployeeRequest;
import com.course.users.domain.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IUserEmployeeRequestMapper {
    @Mapping(target = "idRole", source = "role")
    User toUser(UserEmployeeRequest userEmployeeRequest);
}
