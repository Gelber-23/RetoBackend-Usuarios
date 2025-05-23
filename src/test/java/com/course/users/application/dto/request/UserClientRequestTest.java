package com.course.users.application.dto.request;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UserClientRequestTest {
    private Validator validator;

    @BeforeEach
    void setup() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @ParameterizedTest
    @CsvSource({
            // name, lastName, docNum, phone, email, password, role, expectedValid
            "'Ana', 'Pérez', '1234567', '3123456789', 'ana@mail.com', 'pwd123', 3, true",
            "'', 'Pérez', '1234567', '3123456789', 'ana@mail.com', 'pwd123', 3, false",
            "'Ana', '', '1234567', '3123456789', 'ana@mail.com', 'pwd123', 3, false",
            "'Ana', 'Pérez', '', '3123456789', 'ana@mail.com', 'pwd123', 3, false",
            "'Ana', 'Pérez', 'abc', '3123456789', 'ana@mail.com', 'pwd123', 3, false",
            "'Ana', 'Pérez', '1234567', '', 'ana@mail.com', 'pwd123', 3, false",
            "'Ana', 'Pérez', '1234567', 'abc', 'ana@mail.com', 'pwd123', 3, false",
            "'Ana', 'Pérez', '1234567', '3123456789', '', 'pwd123', 3, false",
            "'Ana', 'Pérez', '1234567', '3123456789', 'not-email', 'pwd123', 3, false",
            "'Ana', 'Pérez', '1234567', '3123456789', 'ana@mail.com', '', 3, false"
    })
    void testUserClientValidation(String name, String lastName, String docNum, String phone,
                                  String email, String password, int role, boolean expectedValid) {
        UserClientRequest req = new UserClientRequest();
        req.setName(name);
        req.setLastName(lastName);
        req.setDocumentNumber(docNum);
        req.setPhone(phone);
        req.setEmail(email);
        req.setPassword(password);
        req.setRole(role);

        Set<ConstraintViolation<UserClientRequest>> violations = validator.validate(req);
        assertEquals(expectedValid, violations.isEmpty());
    }

}