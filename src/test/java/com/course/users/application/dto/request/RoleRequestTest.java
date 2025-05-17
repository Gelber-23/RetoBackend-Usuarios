package com.course.users.application.dto.request;


import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.validation.Validator;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class RoleRequestTest {
    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void validRoleRequest_noViolations() {
        RoleRequest req = new RoleRequest();
        req.setName("Ad");
        req.setDescription("Valid description");
        Set<ConstraintViolation<RoleRequest>> violations = validator.validate(req);
        assertTrue(violations.isEmpty());
    }

    @Test
    void invalidRoleRequest_blankFields() {
        RoleRequest req = new RoleRequest();
        req.setName("2");
        req.setDescription("abcd");
        Set<ConstraintViolation<RoleRequest>> violations = validator.validate(req);
        assertEquals(2, violations.size());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("name")));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("description")));
    }


}