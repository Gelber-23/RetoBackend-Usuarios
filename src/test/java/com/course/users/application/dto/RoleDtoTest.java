package com.course.users.application.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoleDtoTest {
    private RoleDto dto;

    @BeforeEach
    void setUp() {
        dto = new RoleDto();
        dto.setName("USER");
        dto.setDescription("Standard user role");
    }

    @Test
    void gettersReturnExpectedValues() {
        assertEquals("USER", dto.getName());
        assertEquals("Standard user role", dto.getDescription());
    }

}