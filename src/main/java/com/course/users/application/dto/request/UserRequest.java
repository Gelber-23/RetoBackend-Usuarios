package com.course.users.application.dto.request;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UserRequest {

    @NotBlank(message = "Name is required")
    @Size(min = 2, message = "The name must have at least 2 characters")
    private String name;

    @NotBlank(message = "Last Name is required")
    @Size(min = 2, message = "The last name must have at least 2 characters")
    private String lastName;


    @NotBlank(message = "Document is required")
    @Size(min = 7, max = 10, message = "The document must have between 7 and 10 characters")
    @Pattern(regexp = "^[A-Z0-9]+$", message = "The document must only contain capital letters and numbers, without spaces.")
    private String documentNumber;


    @NotBlank(message = "Phone is required")
    @Pattern(regexp = "^\\+?\\d{13,18}$", message = "The phone number must contain only digits and can start with '+'.")
    private  String phone;

    @NotBlank(message = "Birth Date is required")
    private Date birthdate;

    @NotBlank(message = "Email is required")
    @Email(message = "The email is not in a valid format")
    private String email;


    @NotBlank(message = "Password is required")
    private String password;

    private int rol;
}
