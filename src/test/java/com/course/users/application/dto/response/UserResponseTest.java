package com.course.users.application.dto.response;

import com.course.users.application.dto.RoleDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class UserResponseTest {
    private UserResponse userResponse;

    @BeforeEach
    void setUp() {
        RoleDto role = new RoleDto();
        role.setName("USER");
        role.setDescription("Standard");

        userResponse = new UserResponse();
        userResponse.setId(100L);
        userResponse.setName("Alice");
        userResponse.setLastName("Wonderland");
        userResponse.setDocumentNumber("987654321");
        userResponse.setPhone("+341234567890");
        Date birth = Date.from(LocalDate.of(1990, 1, 1).atStartOfDay(ZoneId.systemDefault()).toInstant());
        userResponse.setBirthdate(birth);
        userResponse.setEmail("alice@example.com");
        userResponse.setRol(role);
    }

    @Test
    void gettersReturnExpectedValues() {
        assertEquals(100L, userResponse.getId());
        assertEquals("Alice", userResponse.getName());
        assertEquals("Wonderland", userResponse.getLastName());
        assertEquals("987654321", userResponse.getDocumentNumber());
        assertEquals("+341234567890", userResponse.getPhone());
        assertNotNull(userResponse.getBirthdate());
        assertEquals("alice@example.com", userResponse.getEmail());
        assertNotNull(userResponse.getRol());
        assertEquals("USER", userResponse.getRol().getName());
    }
}