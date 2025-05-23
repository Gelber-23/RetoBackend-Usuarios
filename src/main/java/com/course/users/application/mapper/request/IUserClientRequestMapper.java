package com.course.users.application.mapper.request;

import com.course.users.application.dto.request.UserClientRequest;
import com.course.users.domain.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IUserClientRequestMapper {
    @Mapping(target = "idRole", source = "role")
    User toUser(UserClientRequest userClientRequest);
}
