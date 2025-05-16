package com.course.users.infraestructure.output.jpa.mapper;


import com.course.users.domain.model.User;
import com.course.users.infraestructure.output.jpa.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE )
public interface IUserEntityMapper {


    @Mapping(target = "idRole.id", source = "idRole")
    UserEntity toEntity(User user);
    @Mapping(target = "idRole", source = "idRole.id")
    User toUserModel(UserEntity userEntity);

    List<User> toUserModelList(List<UserEntity> userEntitiesList);

}
