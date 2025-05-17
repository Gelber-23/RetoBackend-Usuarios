package com.course.users.application.dto.response;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoleResponseTest {
    private RoleResponse roleResponse;

    @BeforeEach
    void setUp() {
        roleResponse = new RoleResponse();
        roleResponse.setId(10);
        roleResponse.setName("MANAGER");
        roleResponse.setDescription("Manages teams");
    }

    @Test
    void gettersReturnExpectedValues() {
        assertEquals(10, roleResponse.getId());
        assertEquals("MANAGER", roleResponse.getName());
        assertEquals("Manages teams", roleResponse.getDescription());
    }




}