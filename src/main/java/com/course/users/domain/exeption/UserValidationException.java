package com.course.users.domain.exeption;

import java.util.List;

public class UserValidationException  extends RuntimeException {
    public UserValidationException(List<String> errors) {
        super("Errors: " + errors);
    }

}