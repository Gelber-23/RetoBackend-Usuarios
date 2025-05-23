package com.course.users.infraestructure.utils;

import com.course.users.domain.utils.constants.ValuesConstants;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UtilsAdapterTest {
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private UtilsAdapter utilsAdapter;

    @Test
    void encrypPassword_ShouldCallPasswordEncoderAndReturnEncryptedValue() {
        String rawPassword = "myPassword123";
        String expectedEncrypted = "encryptedPassword123";

        when(passwordEncoder.encode(rawPassword)).thenReturn(expectedEncrypted);

        String result = utilsAdapter.encrypPassword(rawPassword);

        assertEquals(expectedEncrypted, result);
        verify(passwordEncoder).encode(rawPassword);
    }

    @Test
    void isLegal_ShouldReturnTrue_WhenUserIs18OrOlder() {
        LocalDate birthDate = LocalDate.now().minusYears(ValuesConstants.YEARS_TO_BE_LEGAL).minusDays(1);
        Date date = Date.from(birthDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        assertTrue(utilsAdapter.isLegal(date));
    }

    @Test
    void isLegal_ShouldReturnFalse_WhenUserIsUnder18() {
        LocalDate birthDate = LocalDate.now().minusYears(ValuesConstants.YEARS_TO_BE_LEGAL).plusDays(1);
        Date date = Date.from(birthDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        assertFalse(utilsAdapter.isLegal(date));
    }

    @Test
    void isLegal_ShouldReturnFalse_WhenDateIsNull() {
        assertFalse(utilsAdapter.isLegal(null));
    }
}