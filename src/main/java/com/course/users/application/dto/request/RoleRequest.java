package com.course.users.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleRequest {
    @NotBlank(message = "Name is required")
    @Size(min = 2, message = "The name must have at least 2 characters")
    private String name;

    @NotBlank(message = "Description is required")
    @Size(min = 5, message = "Description have at least 5 characters")
    private String description;

}
