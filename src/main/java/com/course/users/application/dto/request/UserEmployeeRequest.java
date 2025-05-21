package com.course.users.application.dto.request;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserEmployeeRequest {

    @NotBlank(message = "Name is required")
    @Size(min = 2, message = "The name must have at least 2 characters")
    private String name;

    @NotBlank(message = "Last Name is required")
    @Size(min = 2, message = "The last name must have at least 2 characters")
    private String lastName;


    @NotBlank(message = "Document is required")
    @Size(min = 7, max = 10, message = "The document must have between 7 and 10 characters")
    @Pattern(regexp = "^\\d+$", message = "The document must only contain numbers")
    private String documentNumber;


    @NotBlank(message = "Phone is required")
    @Pattern(regexp = "^\\+?\\d{10,18}$", message = "The phone number must contain only digits and can start with '+' and must contain between 10 and 18 characters.")
    private  String phone;


    @NotBlank(message = "Email is required")
    @Email(message = "The email is not in a valid format")
    private String email;


    @NotBlank(message = "Password is required")
    private String password;

    @NotNull(message = "ID role is required")
    @Min(value = 1, message = "The id role id cannot be negative or 0")
    private int role;
}
