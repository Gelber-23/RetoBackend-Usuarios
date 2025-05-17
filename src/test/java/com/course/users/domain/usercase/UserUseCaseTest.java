package com.course.users.domain.usercase;

import com.course.users.domain.model.User;
import com.course.users.domain.spi.IUserPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class UserUseCaseTest {

    @Mock
    private IUserPersistencePort userPersistencePort;

    @InjectMocks
    private UserUseCase userUseCase;

    private User sampleUser;

    @BeforeEach
    void setUp() {
        sampleUser = new User();
        sampleUser.setId(1L);
        sampleUser.setName("Test");
        sampleUser.setLastName("Testing");
        sampleUser.setDocumentNumber("123412");
        sampleUser.setPhone("+51222333555");
        sampleUser.setBirthdate(new java.util.Date());
        sampleUser.setEmail("test@example.com");
        sampleUser.setPassword("secret");
        sampleUser.setIdRole(3);
    }
    @Test
    void saveUser() {
        userUseCase.saveUser(sampleUser);
        verify(userPersistencePort, times(1)).saveUser(sampleUser);
    }

    @Test
    void getUserById() {
        when(userPersistencePort.getUserById(1L)).thenReturn(sampleUser);
        User result = userUseCase.getUserById(1L);
        assertNotNull(result);
        assertEquals(sampleUser.getId(), result.getId());
        verify(userPersistencePort).getUserById(1L);
    }

    @Test
    void getAllUsers() {
        User other = new User();
        other.setId(2L);
        List<User> userList = Arrays.asList(sampleUser, other);
        when(userPersistencePort.getAllUsers()).thenReturn(userList);

        List<User> result = userUseCase.getAllUsers();
        assertEquals(2, result.size());
        assertTrue(result.contains(sampleUser));
        verify(userPersistencePort).getAllUsers();
    }

    @Test
    void deleteUserById() {
        userUseCase.deleteUserById(1L);
        verify(userPersistencePort).deleteUserById(1L);
    }

}