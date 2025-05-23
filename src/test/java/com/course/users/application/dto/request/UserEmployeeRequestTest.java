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

class UserEmployeeRequestTest {
    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @ParameterizedTest
    @CsvSource({
            // name, lastName, documentNumber, phone, password, role, expectedValid
            "'Ana', 'Pérez', '1234567', '3123456789', 'pwd123', 3, true",
            "'', 'Pérez', '1234567', '3123456789', 'pwd123', 3, false",
            "'Ana', '', '1234567', '3123456789', 'pwd123', 3, false",
            "'Ana', 'Pérez', '', '3123456789', 'pwd123', 3, false",
            "'Ana', 'Pérez', 'abc1234', '3123456789', 'pwd123', 3, false",
            "'Ana', 'Pérez', '1234567', '', 'pwd123', 3, false",
            "'Ana', 'Pérez', '1234567', 'abc', 'pwd123', 3, false",
            "'Ana', 'Pérez', '1234567', '3123456789', '', 3, false",
            "'Ana', 'Pérez', '1234567', '3123456789', 'pwd123', 0, true", // Role 0 assuming valid int role
    })
    void testUserEmployeeRequestValidation(String name, String lastName, String documentNumber,
                                           String phone, String password, int role, boolean expectedValid) {

        UserEmployeeRequest req = new UserEmployeeRequest();
        req.setName(name);
        req.setLastName(lastName);
        req.setDocumentNumber(documentNumber);
        req.setPhone(phone);
        req.setPassword(password);
        req.setRole(role);

        Set<ConstraintViolation<UserEmployeeRequest>> violations = validator.validate(req);
        assertEquals(expectedValid, violations.isEmpty());
    }
}