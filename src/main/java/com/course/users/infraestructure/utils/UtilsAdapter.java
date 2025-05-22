package com.course.users.infraestructure.utils;

import com.course.users.domain.model.User;
import com.course.users.domain.spi.IUtilsPort;
import com.course.users.domain.utils.constants.ValuesConstants;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Component
@AllArgsConstructor
public class UtilsAdapter implements IUtilsPort {

    private final PasswordEncoder passwordEncoder;
    @Override
    public String encrypPassword(String password) {

        return passwordEncoder.encode(password);
    }

    @Override
    public Boolean isLegal(Date date) {
        if (date == null) {
            return false;
        }

        LocalDate birthDate = Instant.ofEpochMilli(date.getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDate();

        LocalDate today = LocalDate.now();
        long years = ChronoUnit.YEARS.between(birthDate, today);

        return years >= ValuesConstants.YEARS_TO_BE_LEGAL;
    }
}
