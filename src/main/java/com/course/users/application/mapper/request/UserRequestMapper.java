package com.course.users.application.mapper.request;


import com.course.users.application.dto.request.UserRequest;
import com.course.users.domain.model.Role;
import com.course.users.domain.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface UserRequestMapper {
    @Mapping(target = "idRole", source = "rol")
    User toUser(UserRequest userRequest);

}
