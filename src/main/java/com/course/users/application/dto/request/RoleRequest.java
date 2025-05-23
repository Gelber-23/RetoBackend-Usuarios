package com.course.users.application.dto.request;

import com.course.users.domain.utils.constants.DtoConstants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleRequest {

    @NotBlank(message = DtoConstants.FIELD_REQUIRED)
    @Size(min = 2, message =  DtoConstants.FIELD_MIN_2_SIZE_REQUIRED)
    private String name;

    @NotBlank(message =  DtoConstants.FIELD_REQUIRED)
    @Size(min = 2, message = DtoConstants.FIELD_MIN_2_SIZE_REQUIRED)
    private String description;
}
