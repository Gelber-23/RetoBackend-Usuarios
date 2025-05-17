package com.course.users.application.dto.request.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class AdultValidator  implements ConstraintValidator<Adult, Date> {
    @Override
    public boolean isValid(Date value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        LocalDate birthDate = Instant.ofEpochMilli(value.getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        LocalDate today = LocalDate.now();
        long years = ChronoUnit.YEARS.between(birthDate, today);
        return years >= 18;
    }
}
