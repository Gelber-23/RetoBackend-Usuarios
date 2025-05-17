package com.course.users.infraestructure.output.jpa.Adapter;

import com.course.users.domain.model.User;
import com.course.users.infraestructure.exception.NoDataFoundException;
import com.course.users.infraestructure.output.jpa.entity.RoleEntity;
import com.course.users.infraestructure.output.jpa.entity.UserEntity;
import com.course.users.infraestructure.output.jpa.mapper.IUserEntityMapper;
import com.course.users.infraestructure.output.jpa.repository.IUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserJpaAdapterTest {

    @Mock
    private IUserRepository userRepository;

    @Mock
    private IUserEntityMapper userEntityMapper;

    @InjectMocks
    private UserJpaAdapter adapter;

    private User user;
    private UserEntity entity;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setName("User");
        user.setLastName("Test");
        user.setDocumentNumber("1234567");
        user.setPhone("+51987654321");
        user.setBirthdate(new Date());
        user.setEmail("user@example.com");
        user.setPassword("secret");
        user.setIdRole(2);


        entity = new UserEntity();
        entity.setId(1L);
        entity.setName("User");
        entity.setLastName("Test");
        entity.setDocumentNumber("1234567");
        entity.setPhone("+51987654321");
        entity.setBirthdate(new Date());
        entity.setEmail("user@example.com");
        entity.setPassword("secret");
        RoleEntity role = new RoleEntity();
        role.setId(2);
        entity.setIdRole(role);
    }

    @Test
    void shouldSaveUser() {

        when(userEntityMapper.toEntity(user)).thenReturn(entity);


        adapter.saveUser(user);


        verify(userEntityMapper).toEntity(user);
        verify(userRepository).save(entity);
    }

    @Test
    void shouldGetUserByIdSuccessfully() {

        when(userRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(userEntityMapper.toUserModel(entity)).thenReturn(user);


        User result = adapter.getUserById(1L);


        assertEquals(user, result);
        verify(userRepository).findById(1L);
        verify(userEntityMapper).toUserModel(entity);
    }

    @Test
    void getUserByIdThrowsWhenNotFound() {

        when(userRepository.findById(1L)).thenReturn(Optional.empty());


        assertThrows(NoDataFoundException.class, () -> adapter.getUserById(1L));
        verify(userRepository).findById(1L);
        verify(userEntityMapper, never()).toUserModel(any());
    }

    @Test
    void shouldGetAllUsersSuccessfully() {

        List<UserEntity> entities = Arrays.asList(entity);
        List<User> domains = Arrays.asList(user);

        when(userRepository.findAll()).thenReturn(entities);
        when(userEntityMapper.toUserModelList(entities)).thenReturn(domains);


        List<User> result = adapter.getAllUsers();


        assertEquals(domains, result);
        verify(userRepository).findAll();
        verify(userEntityMapper).toUserModelList(entities);
    }

    @Test
    void getAllUsersThrowsWhenEmpty() {

        when(userRepository.findAll()).thenReturn(Collections.emptyList());


        assertThrows(NoDataFoundException.class, () -> adapter.getAllUsers());
        verify(userRepository).findAll();
        verify(userEntityMapper, never()).toUserModelList(anyList());
    }

    @Test
    void shouldDeleteUserById() {

        adapter.deleteUserById(1L);

        verify(userRepository).deleteById(1L);
    }
}