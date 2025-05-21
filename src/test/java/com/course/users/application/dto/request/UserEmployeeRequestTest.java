package com.course.users.application.dto.request;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UserEmployeeRequestTest {

    private static Validator validator;

    @BeforeAll
    public static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    private UserEmployeeRequest buildValidRequest() {
        UserEmployeeRequest req = new UserEmployeeRequest();
        req.setName("Prueba");
        req.setLastName("Nueva");
        req.setDocumentNumber("1234567");
        req.setPhone("+12345678901");
        req.setEmail("test@example.com");
        req.setPassword("securePassword");
        req.setRole(1);
        return req;
    }

    @Test
    void whenAllFieldsValid_thenNoViolations() {
        UserEmployeeRequest req = buildValidRequest();
        Set<ConstraintViolation<UserEmployeeRequest>> violations = validator.validate(req);
        assertTrue(violations.isEmpty(), "Expected no constraint violations");
    }

    @Test
    void whenNameBlank_thenViolation() {
        UserEmployeeRequest req = buildValidRequest();
        req.setName("");
        Set<ConstraintViolation<UserEmployeeRequest>> violations = validator.validate(req);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("name")
                        && v.getMessage().contains("Name is required")));
    }

    @Test
    void whenNameTooShort_thenViolation() {
        UserEmployeeRequest req = buildValidRequest();
        req.setName("A");
        Set<ConstraintViolation<UserEmployeeRequest>> violations = validator.validate(req);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("name")
                        && v.getMessage().contains("at least 2 characters")));
    }

    @Test
    void whenDocumentNotNumeric_thenViolation() {
        UserEmployeeRequest req = buildValidRequest();
        req.setDocumentNumber("ABC1234");
        Set<ConstraintViolation<UserEmployeeRequest>> violations = validator.validate(req);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("documentNumber")
                        && v.getMessage().contains("only contain numbers")));
    }

    @Test
    void whenDocumentTooShort_thenViolation() {
        UserEmployeeRequest req = buildValidRequest();
        req.setDocumentNumber("123");
        Set<ConstraintViolation<UserEmployeeRequest>> violations = validator.validate(req);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("documentNumber")
                        && v.getMessage().contains("between 7 and 10 characters")));
    }

    @Test
    void whenPhoneInvalid_thenViolation() {
        UserEmployeeRequest req = buildValidRequest();
        req.setPhone("12345");
        Set<ConstraintViolation<UserEmployeeRequest>> violations = validator.validate(req);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("phone")
                        && v.getMessage().contains("must contain only digits")));
    }

    @Test
    void whenEmailInvalid_thenViolation() {
        UserEmployeeRequest req = buildValidRequest();
        req.setEmail("not-an-email");
        Set<ConstraintViolation<UserEmployeeRequest>> violations = validator.validate(req);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("email")
                        && v.getMessage().contains("not in a valid format")));
    }

    @Test
    void whenPasswordBlank_thenViolation() {
        UserEmployeeRequest req = buildValidRequest();
        req.setPassword("");
        Set<ConstraintViolation<UserEmployeeRequest>> violations = validator.validate(req);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("password")
                        && v.getMessage().contains("Password is required")));
    }

    @Test
    void whenRoleZero_thenViolation() {
        UserEmployeeRequest req = buildValidRequest();
        req.setRole(0);
        Set<ConstraintViolation<UserEmployeeRequest>> violations = validator.validate(req);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("role")
                        && v.getMessage().contains("cannot be negative or 0")));
    }
}