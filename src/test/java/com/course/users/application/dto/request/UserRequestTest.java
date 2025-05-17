package com.course.users.application.dto.request;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UserRequestTest {
    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }
    @Test
    void validUserRequest_noViolations() {
        UserRequest req = new UserRequest();
        req.setName("John");
        req.setLastName("Doe");
        req.setDocumentNumber("1234567");
        req.setPhone("+12345678901");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, -18);
        req.setBirthdate(cal.getTime());
        req.setEmail("john.doe@example.com");
        req.setPassword("secret");
        req.setRol(1);
        Set<ConstraintViolation<UserRequest>> violations = validator.validate(req);
        assertTrue(violations.isEmpty());
    }

    @Test
    void invalidUserRequest_multipleViolations() {
        UserRequest req = new UserRequest();
        req.setName("A");
        req.setLastName("");
        req.setDocumentNumber("abc");
        req.setPhone("123");
        req.setBirthdate(null);
        req.setEmail("invalid-email");
        req.setPassword("");
        req.setRol(0);
        Set<ConstraintViolation<UserRequest>> violations = validator.validate(req);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("name")));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("lastName")));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("documentNumber")));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("phone")));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("birthdate")));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("email")));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("password")));
    }
}