package com.course.users.application.dto.request;

import com.course.users.application.dto.request.validator.Adult;
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
    @Pattern(regexp = "^\\d+$", message = "The document must only contain numbers")
    private String documentNumber;


    @NotBlank(message = "Phone is required")
    @Pattern(regexp = "^\\+?\\d{10,18}$", message = "The phone number must contain only digits and can start with '+' and must contain between 10 and 18 characters.")
    private  String phone;

    @NotNull(message = "Birth Date is required")
    @Adult(message = "Must be at least 18 years old")
    private Date birthdate;

    @NotBlank(message = "Email is required")
    @Email(message = "The email is not in a valid format")
    private String email;


    @NotBlank(message = "Password is required")
    private String password;

    private int rol;
}
