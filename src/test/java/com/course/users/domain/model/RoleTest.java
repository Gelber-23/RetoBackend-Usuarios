package com.course.users.domain.model;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class RoleTest {
    private Role role;
    private Role roleConstructor;

    @BeforeEach
    void setUp() {

        role = new Role();
        role.setId(1);
        role.setName("Test");
        role.setDescription("New");
        roleConstructor = new Role(1,"Test" , "New");
    }

    @Test
    void gettersReturnExpectedValuesRole() {
        assertEquals(1, role.getId());
        assertEquals("Test", role.getName());
        assertEquals("New", role.getDescription());

    }
    @Test
    void compareRoles() {
        assertEquals( roleConstructor.getName(), role.getName());

    }
}