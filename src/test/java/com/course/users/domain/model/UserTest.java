package com.course.users.domain.model;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    private User user;
    private User userConstructor;
    private Date birthdate;

    @BeforeEach
    void setUp() {
        Calendar cal = Calendar.getInstance();
        cal.set(1990, Calendar.JANUARY, 1, 0, 0, 0);
        cal.set(Calendar.MILLISECOND, 0);
        birthdate = cal.getTime(); // Jan 1, 1990
        user = new User();
        user.setId(1L);
        user.setName("Test");
        user.setLastName("New");
        user.setDocumentNumber("12332123");
        user.setPhone("123456107");
        user.setBirthdate(birthdate);
        user.setEmail("test@example.com");
        user.setPassword("password");
        user.setIdRole(3);

         userConstructor= new User(1L,"Test","New","12332123","123456107",birthdate,"test@example.com","password",3);
    }

    @Test
    void gettersReturnExpectedValues() {
        assertEquals(1L, user.getId());
        assertEquals("Test", user.getName());
        assertEquals("New", user.getLastName());
        assertEquals("12332123", user.getDocumentNumber());
        assertEquals("123456107", user.getPhone());
        assertEquals(birthdate, user.getBirthdate());
        assertEquals("test@example.com", user.getEmail());
        assertEquals("password", user.getPassword());
        assertEquals(3, user.getIdRole());
    }
    @Test
    void compareUsers() {
        assertEquals( userConstructor.getName(), user.getName());

    }
  
}