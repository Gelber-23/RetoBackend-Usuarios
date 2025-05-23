package com.course.users.application.mapper.response;

import com.course.users.application.dto.response.UserResponse;
import com.course.users.domain.model.Role;
import com.course.users.domain.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        uses = {RoleResponseMapper.class} )

public interface UserResponseMapper {

    RoleResponseMapper INSTANCE = Mappers.getMapper(RoleResponseMapper.class);

    @Mapping(target = "id", source = "user.id")
    @Mapping(target = "name", source = "user.name")
    @Mapping(target = "lastName", source = "user.lastName")
    @Mapping(target = "documentNumber", source = "user.documentNumber")
    @Mapping(target = "phone", source = "user.phone")
    @Mapping(target = "birthdate", source = "user.birthdate")
    @Mapping(target = "email", source = "user.email")
    @Mapping(target = "rol.id", source = "roleDto.id")
    @Mapping(target = "rol.name", source = "roleDto.name")
    @Mapping(target = "rol.description", source = "roleDto.description")
    UserResponse toResponse(User user, Role roleDto);

    default List<UserResponse> toResponseList(List<User> userList, List<Role> roleList) {
        return userList.stream()
                .map(user -> {
                    UserResponse userResponse = new UserResponse();
                    userResponse.setId(user.getId());
                    userResponse.setName(user.getName());
                    userResponse.setLastName(user.getLastName());
                    userResponse.setDocumentNumber(user.getDocumentNumber());
                    userResponse.setPhone(user.getPhone());
                    userResponse.setBirthdate(user.getBirthdate());
                    userResponse.setEmail(user.getEmail());
                    userResponse.setRol(INSTANCE.toResponse(roleList.stream().filter(role -> role.getId() == user.getIdRole() ).findFirst().orElse(null)));
                    return userResponse;
                }).toList();
    }


}
