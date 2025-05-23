package com.course.users.infraestructure.exceptionhandler;

import com.course.users.domain.exeption.JwtNotCreate;
import com.course.users.infraestructure.exception.NoDataFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ControllerAdvisorTest {
    private ControllerAdvisor advisor;

    @BeforeEach
    void setUp() {
        advisor = new ControllerAdvisor();
    }

    @Test
    void handleNoDataFoundException_returnsNotFoundAndNoDataMessage() {
        NoDataFoundException ex = new NoDataFoundException();
        ResponseEntity<Map<String, String>> response = advisor.handleNoDataFoundException(ex);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        Map<String, String> body = response.getBody();
        assertNotNull(body);
        assertEquals(
                ExceptionResponse.NO_DATA_FOUND.getMessage(),
                body.get("Message")
        );
    }

    @Test
    void handleValidationErrors_returnsBadRequestAndFieldErrors() {

        FieldError fe1 = new FieldError("obj", "field1", "must not be blank");
        FieldError fe2 = new FieldError("obj", "field2", "size must be >= 2");
        List<FieldError> errors = Arrays.asList(fe1, fe2);


        MethodArgumentNotValidException ex = mock(MethodArgumentNotValidException.class);
        BindingResult bindingResult = mock(BindingResult.class);
        when(ex.getBindingResult()).thenReturn(bindingResult);
        when(bindingResult.getFieldErrors()).thenReturn(errors);

        ResponseEntity<Map<String, String>> response = advisor.handleValidationErrors(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Map<String, String> body = response.getBody();
        assertNotNull(body);
        assertEquals(2, body.size());
        assertEquals("must not be blank", body.get("field1"));
        assertEquals("size must be >= 2", body.get("field2"));
    }

    @Test
    void handleJwtNotCreate_returnsNotFoundAndExceptionMessage() {
        String msg = "could not create JWT";
        JwtNotCreate ex = new JwtNotCreate(msg);

        ResponseEntity<Map<String, String>> response = advisor.handleUserNotFound(ex);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        Map<String, String> body = response.getBody();
        assertNotNull(body);
        assertEquals(msg, body.get("Message"));
    }
}