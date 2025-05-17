package com.course.users.domain.usercase;


import com.course.users.domain.model.Role;
import com.course.users.domain.spi.IRolePersistencePort;
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
class RoleUseCaseTest {
    @Mock
    private IRolePersistencePort rolePersistencePort;

    @InjectMocks
    private RoleUseCase roleUseCase;

    private Role sampleRole;

    @BeforeEach
    void setUp() {
        sampleRole = new Role();
        sampleRole.setId(1);
        sampleRole.setName("ADMIN");
        sampleRole.setDescription("administrator");
    }

    @Test
    void saveRole() {
        roleUseCase.saveRole(sampleRole);
        verify(rolePersistencePort, times(1)).saveRole(sampleRole);
    }

    @Test
    void getRoleById() {
        when(rolePersistencePort.getRoleById(1)).thenReturn(sampleRole);
        Role result = roleUseCase.getRoleById(1);
        assertNotNull(result);
        assertEquals(sampleRole.getId(), result.getId());
        verify(rolePersistencePort).getRoleById(1);
    }

    @Test
    void getAllRoles() {
        Role other = new Role();
        other.setId(2);
        List<Role> roleList = Arrays.asList(sampleRole, other);
        when(rolePersistencePort.getAllRoles()).thenReturn(roleList);

        List<Role> result = roleUseCase.getAllRoles();
        assertEquals(2, result.size());
        assertTrue(result.contains(sampleRole));
        verify(rolePersistencePort).getAllRoles();
    }

    @Test
    void deleteRoleById() {
        roleUseCase.deleteRoleById(1);
        verify(rolePersistencePort).deleteRoleById(1);
    }
}