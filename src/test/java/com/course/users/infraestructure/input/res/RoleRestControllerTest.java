package com.course.users.infraestructure.input.res;

import com.course.users.application.dto.request.RoleRequest;
import com.course.users.application.dto.response.RoleResponse;
import com.course.users.application.handler.IRoleHandler;
import com.course.users.application.mapper.response.RoleResponseMapper;
import com.course.users.domain.model.Role;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RoleRestControllerTest {
    @Mock
    private IRoleHandler roleHandler;
    @Mock
    private RoleResponseMapper roleResponseMapper;

    @InjectMocks
    private RoleRestController roleRestController;

    private Validator validator;

    private Role role;
    @BeforeEach
    void setUp() {

        role = new Role();
        role.setId(1);
        role.setName("Admin");
        role.setDescription("Administrator role");

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }
    @Test
    void shouldSaveRoleSuccessfully() {
        RoleRequest request = new RoleRequest();
        request.setName(role.getName());
        request.setDescription(role.getDescription());

        ResponseEntity<Void> response = roleRestController.saveRole(request);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(roleHandler).saveRole(request);
    }

    @Test
    void shouldGetRoleByIdSuccessfully() {

        RoleResponse mockResponse =  roleResponseMapper.toResponse(role);

        when(roleHandler.getRoleById(role.getId())).thenReturn(mockResponse);

        ResponseEntity<RoleResponse> response = roleRestController.getRoleById(role.getId());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockResponse, response.getBody());
        verify(roleHandler).getRoleById(role.getId());
    }

    @Test
    void shouldGetAllRolesSuccessfully() {
        List<RoleResponse> roles = Arrays.asList( roleResponseMapper.toResponse(role) );

        when(roleHandler.getAllRoles()).thenReturn(roles);

        ResponseEntity<List<RoleResponse>> response = roleRestController.getAllRoles();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        verify(roleHandler).getAllRoles();
    }

    @Test
    void shouldDeleteRoleSuccessfully() {
        int roleId = 1;

        ResponseEntity<Void> response = roleRestController.deleteRoleById(roleId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(roleHandler).deleteRoleById(roleId);
    }

    @Test
    void shouldFailWhenNameIsBlank() {
        RoleRequest request = new RoleRequest();
        request.setName(""); // Invalid
        request.setDescription("Valid description");

        Set<ConstraintViolation<RoleRequest>> violations = validator.validate(request);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("name")));
    }

    @Test
    void shouldFailWhenNameTooShort() {
        RoleRequest request = new RoleRequest();
        request.setName("A"); // Too short
        request.setDescription("Valid description");

        Set<ConstraintViolation<RoleRequest>> violations = validator.validate(request);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v ->
                v.getPropertyPath().toString().equals("name") &&
                        v.getMessage().contains("at least 2 characters")
        ));
    }

    @Test
    void shouldFailWhenDescriptionIsBlank() {
        RoleRequest request = new RoleRequest();
        request.setName("ValidName");
        request.setDescription(""); // Invalid

        Set<ConstraintViolation<RoleRequest>> violations = validator.validate(request);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("description")));
    }

    @Test
    void shouldFailWhenDescriptionTooShort() {
        RoleRequest request = new RoleRequest();
        request.setName("ValidName");
        request.setDescription("123"); // Too short

        Set<ConstraintViolation<RoleRequest>> violations = validator.validate(request);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v ->
                v.getPropertyPath().toString().equals("description") &&
                        v.getMessage().contains("at least")
        ));
    }
}