package com.course.users.infraestructure.input.res;

import com.course.users.application.dto.RoleDto;
import com.course.users.application.dto.request.RoleRequest;
import com.course.users.application.dto.request.UserRequest;
import com.course.users.application.dto.response.UserResponse;
import com.course.users.application.handler.IUserHandler;
import com.course.users.application.mapper.request.UserRequestMapper;
import com.course.users.application.mapper.response.UserResponseMapper;
import com.course.users.domain.model.User;
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

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserRestControllerTest {


    @Mock
    private IUserHandler userHandler;

    @Mock
    private UserRequestMapper userRequestMapper;

    @Mock
    private UserResponseMapper userResponseMapper;

    @InjectMocks
    private UserRestController userRestController;

    private UserRequest userRequest;
    private UserResponse userResponse;
    private Validator validator;

    @BeforeEach
    void setUp() {

        User user = new User();
        user.setId(1L);
        user.setName("Juan");
        user.setLastName("Perez");
        user.setDocumentNumber("1234567");
        user.setPhone("+51987654321");
        user.setBirthdate(new Date(System.currentTimeMillis() - 1000L * 60 * 60 * 24 * 365 * 20));
        user.setEmail("juan.perez@example.com");
        user.setPassword("secret");
        user.setIdRole(2);


        userRequest = new UserRequest();
        userRequest.setName(user.getName());
        userRequest.setLastName(user.getLastName());
        userRequest.setDocumentNumber(user.getDocumentNumber());
        userRequest.setPhone(user.getPhone());
        userRequest.setBirthdate(user.getBirthdate());
        userRequest.setEmail(user.getEmail());
        userRequest.setPassword(user.getPassword());
        userRequest.setRol(user.getIdRole());


        userResponse = new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setName(user.getName());
        userResponse.setLastName(user.getLastName());
        userResponse.setDocumentNumber(user.getDocumentNumber());
        userResponse.setPhone(user.getPhone());
        userResponse.setBirthdate(user.getBirthdate());
        userResponse.setEmail(user.getEmail());
        RoleDto rd = new RoleDto();
        rd.setName("User");
        rd.setDescription("Normal User");
        userResponse.setRol(rd);

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void shouldSaveUserSuccessfully() {


        ResponseEntity<Void> response = userRestController.saveUser(userRequest);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(userHandler).saveUser(userRequest);
    }

    @Test
    void shouldGetUserByIdSuccessfully() {
        when(userHandler.getUserById(1L)).thenReturn(userResponse);

        ResponseEntity<UserResponse> response = userRestController.getUserById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userResponse, response.getBody());
        verify(userHandler).getUserById(1L);
    }

    @Test
    void shouldGetAllUsersSuccessfully() {
        List<UserResponse> list =  Arrays.asList(userResponse);
        when(userHandler.getAllUsers()).thenReturn(list);

        ResponseEntity<List<UserResponse>> response = userRestController.getAllUsers();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        verify(userHandler).getAllUsers();
    }

    @Test
    void shouldDeleteUserByIdSuccessfully() {
        ResponseEntity<Void> response = userRestController.deleteUserById(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(userHandler).deleteUserById(1L);
    }


    @Test
    void nameBlankFails() {
        UserRequest r = userRequest;
        r.setName("");
        Set<ConstraintViolation<UserRequest>> errs = validator.validate(r);
        assertTrue(errs.stream().anyMatch(e -> e.getPropertyPath().toString().equals("name")));
    }

    @Test
    void nameTooShortFails() {
        UserRequest r = userRequest;
        r.setName("A");
        Set<ConstraintViolation<UserRequest>> errs = validator.validate(r);
        assertTrue(errs.stream().anyMatch(e ->
                e.getPropertyPath().toString().equals("name")
                        && e.getMessage().contains("at least")
        ));
    }

    @Test
    void lastNameBlankFails() {
        UserRequest r  = userRequest;
        r.setLastName("");
        Set<ConstraintViolation<UserRequest>> errs = validator.validate(r);
        assertTrue(errs.stream().anyMatch(e -> e.getPropertyPath().toString().equals("lastName")));
    }

    @Test
    void documentNumberNonDigitsFails() {
        UserRequest r =  userRequest;
        r.setDocumentNumber("ABC1234");
        Set<ConstraintViolation<UserRequest>> errs = validator.validate(r);
        assertTrue(errs.stream().anyMatch(e ->
                e.getPropertyPath().toString().equals("documentNumber")
                        && e.getMessage().contains("only contain numbers")
        ));
    }

    @Test
    void documentNumberTooShortFails() {
        UserRequest r  = userRequest;
        r.setDocumentNumber("123456");
        Set<ConstraintViolation<UserRequest>> errs = validator.validate(r);
        assertTrue(errs.stream().anyMatch(e ->
                e.getPropertyPath().toString().equals("documentNumber")
                        && e.getMessage().contains("between")
        ));
    }

    @Test
    void phoneInvalidPatternFails() {
        UserRequest r = userRequest;
        r.setPhone("12345");
        Set<ConstraintViolation<UserRequest>> errs = validator.validate(r);
        assertTrue(errs.stream().anyMatch(e ->
                e.getPropertyPath().toString().equals("phone")
        ));
    }

    @Test
    void birthdateNullFails() {
        UserRequest r  = userRequest;
        r.setBirthdate(null);
        Set<ConstraintViolation<UserRequest>> errs = validator.validate(r);
        assertTrue(errs.stream().anyMatch(e -> e.getPropertyPath().toString().equals("birthdate")));
    }

    @Test
    void underageFails() {
        UserRequest r  = userRequest;

        LocalDate ld = LocalDate.now().minusYears(16);
        r.setBirthdate(Date.from(ld.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        Set<ConstraintViolation<UserRequest>> errs = validator.validate(r);
        assertTrue(errs.stream().anyMatch(e ->
                e.getPropertyPath().toString().equals("birthdate")
                        && e.getMessage().contains("at least 18")
        ));
    }

    @Test
    void emailInvalidFails() {
        UserRequest r  = userRequest;
        r.setEmail("not-an-email");
        Set<ConstraintViolation<UserRequest>> errs = validator.validate(r);
        assertTrue(errs.stream().anyMatch(e ->
                e.getPropertyPath().toString().equals("email")
                        && e.getMessage().contains("valid format")
        ));
    }

    @Test
    void passwordBlankFails() {
        UserRequest r  = userRequest;
        r.setPassword("");
        Set<ConstraintViolation<UserRequest>> errs = validator.validate(r);
        assertTrue(errs.stream().anyMatch(e -> e.getPropertyPath().toString().equals("password")));
    }

}