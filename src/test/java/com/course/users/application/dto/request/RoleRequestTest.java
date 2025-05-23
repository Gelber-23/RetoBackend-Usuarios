package com.course.users.application.dto.request;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class RoleRequestTest {
    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }
    @ParameterizedTest
    @CsvSource({
            "'Admin', 'Valid description', true",
            "' ', 'Valid description', false",
            "'A', 'Valid description', false",
            "'Admin', ' ', false",
            "'Admin', 'X', false"
    })
    void testValidation(String name, String description, boolean expectedValid) {
        RoleRequest role = new RoleRequest();
        role.setName(name);
        role.setDescription(description);

        Set<ConstraintViolation<RoleRequest>> violations = validator.validate(role);
        assertEquals(expectedValid, violations.isEmpty());
    }

}