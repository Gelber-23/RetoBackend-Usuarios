package com.course.users.application.dto.response;

import com.course.users.application.dto.RoleDto;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UserResponse {

    private String name;

    private String lastName;

    private String documentNumber;

    private  String phone;

    private Date birthdate;

    private String email;


    private RoleDto rol;
}
