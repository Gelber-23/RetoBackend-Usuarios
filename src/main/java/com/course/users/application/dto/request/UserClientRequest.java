package com.course.users.application.dto.request;

import com.course.users.application.dto.request.validator.Adult;
import com.course.users.domain.utils.constants.DtoConstants;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UserClientRequest {

    @NotBlank(message = DtoConstants.FIELD_REQUIRED)
    @Size(min = 2, message =  DtoConstants.FIELD_MIN_2_SIZE_REQUIRED)
    private String name;

    @NotBlank(message =  DtoConstants.FIELD_REQUIRED)
    @Size(min = 2, message = DtoConstants.FIELD_MIN_2_SIZE_REQUIRED)
    private String lastName;


    @NotBlank(message =  DtoConstants.FIELD_REQUIRED)
    @Size(min = 7, max = 10, message = DtoConstants.FIELD_BETWEEN_7_10_REQUIRED)
    @Pattern(regexp = DtoConstants.ONLY_NUMBERS_REGEX, message = DtoConstants.FIELD_ONLY_NUMBER_REQUIRED)
    private String documentNumber;


    @NotBlank(message = DtoConstants.FIELD_REQUIRED)
    @Pattern(regexp =DtoConstants.PHONE_REGEX, message = DtoConstants.FIELD_PHONE_ERROR_MESSAGE)
    private  String phone;

    @NotBlank(message =  DtoConstants.FIELD_REQUIRED)
    @Email(message = DtoConstants.FIELD_EMAIL_INCORRECT_FORMAT)
    private String email;


    @NotBlank(message =  DtoConstants.FIELD_REQUIRED)
    private String password;
    @NotNull(message =  DtoConstants.FIELD_REQUIRED)
    private int role;
}
