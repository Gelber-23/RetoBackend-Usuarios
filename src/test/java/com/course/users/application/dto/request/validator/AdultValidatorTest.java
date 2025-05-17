package com.course.users.application.dto.request.validator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class AdultValidatorTest {


    private AdultValidator validator;

    @BeforeEach
    void setUp() {
        validator = new AdultValidator();
    }

    @Test
    void isValid_nullBirthdate_returnsTrue() {
        assertTrue(validator.isValid(null, null));
    }

    @Test
    void isValid_exactlyEighteenYearsAgo_returnsTrue() {
        LocalDate today = LocalDate.now();
        LocalDate eighteenYearsAgo = today.minusYears(18);
        Date date = Date.from(eighteenYearsAgo.atStartOfDay(ZoneId.systemDefault()).toInstant());
        assertTrue(validator.isValid(date, null));
    }

    @Test
    void isValid_lessThanEighteenYearsAgo_returnsFalse() {
        LocalDate today = LocalDate.now();
        LocalDate seventeenYearsAgo = today.minusYears(17).plusDays(1);
        Date date = Date.from(seventeenYearsAgo.atStartOfDay(ZoneId.systemDefault()).toInstant());
        assertFalse(validator.isValid(date, null));
    }

    @Test
    void isValid_moreThanEighteenYearsAgo_returnsTrue() {
        LocalDate today = LocalDate.now();
        LocalDate twentyYearsAgo = today.minusYears(20);
        Date date = Date.from(twentyYearsAgo.atStartOfDay(ZoneId.systemDefault()).toInstant());
        assertTrue(validator.isValid(date, null));
    }
}