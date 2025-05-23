package com.course.users.domain.usercase;

import com.course.users.domain.exeption.UserValidationException;
import com.course.users.domain.model.User;
import com.course.users.domain.spi.IUserPersistencePort;
import com.course.users.domain.spi.IUtilsPort;
import com.course.users.domain.utils.constants.DtoConstants;
import com.course.users.domain.utils.constants.ValuesConstants;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class UserUseCaseTest {
    @Mock
    private IUserPersistencePort persistencePort;
    @Mock
    private IUtilsPort utilsPort;
    @InjectMocks
    private UserUseCase userUseCase;

    private User mockValidUser(Integer roleId) {
        User user = mock(User.class);
        lenient().when(user.getName()).thenReturn("John");
        lenient().when(user.getLastName()).thenReturn("Doe");
        lenient().when(user.getDocumentNumber()).thenReturn("123456789");
        lenient().when(user.getPhone()).thenReturn("3121234567");
        lenient().when(user.getEmail()).thenReturn("john.doe@example.com");
        lenient().when(user.getPassword()).thenReturn("pass123");
        lenient().when(user.getBirthdate()).thenReturn(new Date(System.currentTimeMillis() - 1000L * 60 * 60 * 24 * 365 * 20));

        lenient().when(user.getIdRole()).thenReturn(roleId);
        return user;
    }

    @Test
    void saveUser_OwnerRole_ShouldSaveSuccessfully() {
        User user = mockValidUser(ValuesConstants.ID_ROLE_OWNER);
        when(utilsPort.encrypPassword(any())).thenReturn("encrypted");
        when(utilsPort.isLegal(any())).thenReturn(true);

        userUseCase.saveUser(user);

        verify(user).setPassword("encrypted");
        verify(user).setIdRole(ValuesConstants.ID_ROLE_OWNER);
        verify(persistencePort).saveUser(user);
    }

    @Test
    void save_InvalidFields_ShouldNotSaveAndThrowException() {
        User user = mock(User.class);
        when(user.getName()).thenReturn("");
        when(user.getLastName()).thenReturn("");
        when(user.getDocumentNumber()).thenReturn("");
        when(user.getPhone()).thenReturn("");
        when(user.getEmail()).thenReturn("");
        when(user.getPassword()).thenReturn("");
        when(user.getIdRole()).thenReturn(0);

        assertThrows(UserValidationException.class, () -> userUseCase.saveClient(user));

        verify(persistencePort, never()).saveUser(any());
    }
    @Test
    void save_NullFields_ShouldNotSaveAndThrowException() {
        User user = mock(User.class);
        when(user.getName()).thenReturn(null);
        when(user.getLastName()).thenReturn(null);
        when(user.getDocumentNumber()).thenReturn(null);
        when(user.getPhone()).thenReturn(null);
        when(user.getEmail()).thenReturn(null);
        when(user.getPassword()).thenReturn(null);
        when(user.getIdRole()).thenReturn(0);

        assertThrows(UserValidationException.class, () -> userUseCase.saveClient(user));

        verify(persistencePort, never()).saveUser(any());
    }

    @Test
    void saveUser_InvalidAge_ShouldThrowAndNotSave() {
        User user = mockValidUser(ValuesConstants.ID_ROLE_OWNER);
        when(user.getBirthdate()).thenReturn(new Date());
        when(utilsPort.encrypPassword(any())).thenReturn("encrypted");
        when(utilsPort.isLegal(any())).thenReturn(false);

        assertThrows(UserValidationException.class, () -> userUseCase.saveUser(user));

        verify(persistencePort, never()).saveUser(any());
    }

    @Test
    void getUserById_ShouldReturnCorrectUser() {
        User user = mock(User.class);
        when(persistencePort.getUserById(10L)).thenReturn(user);

        User result = userUseCase.getUserById(10L);
        assertEquals(user, result);
        verify(persistencePort).getUserById(10L);
    }

    @Test
    void getAllUsers_ShouldReturnList() {
        List<User> mockList = Arrays.asList(mock(User.class), mock(User.class));
        when(persistencePort.getAllUsers()).thenReturn(mockList);

        List<User> result = userUseCase.getAllUsers();
        assertEquals(2, result.size());
        verify(persistencePort).getAllUsers();
    }

    @Test
    void deleteUser_ShouldInvokePersistencePort() {
        userUseCase.deleteUserById(5L);
        verify(persistencePort).deleteUserById(5L);
    }
    @Test
    void saveEmployee_ShouldSetEncryptedPasswordAndSave() {
        User user = mockValidUser(ValuesConstants.ID_ROLE_EMPLOYEE);
        when(utilsPort.encrypPassword(any())).thenReturn("encrypted");

        userUseCase.saveEmployee(user);

        verify(user).setPassword("encrypted");
        verify(user).setIdRole(ValuesConstants.ID_ROLE_EMPLOYEE);
        verify(persistencePort).saveUser(user);
    }
}