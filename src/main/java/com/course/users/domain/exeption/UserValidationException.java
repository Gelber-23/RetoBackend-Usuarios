package com.course.users.domain.exeption;

import java.util.List;

public class UserValidationException  extends RuntimeException {
    private final List<String> errors;

    public UserValidationException(List<String> errors) {
        super("Errors: " + errors);
        this.errors = errors;
    }

    public List<String> getErrors() {
        return errors;
    }
}