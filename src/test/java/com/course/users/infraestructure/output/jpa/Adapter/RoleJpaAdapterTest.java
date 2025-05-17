package com.course.users.infraestructure.output.jpa.Adapter;

import com.course.users.domain.model.Role;
import com.course.users.infraestructure.exception.NoDataFoundException;
import com.course.users.infraestructure.output.jpa.entity.RoleEntity;
import com.course.users.infraestructure.output.jpa.mapper.IRoleEntityMapper;
import com.course.users.infraestructure.output.jpa.repository.IRoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoleJpaAdapterTest {
    @Mock
    private IRoleRepository roleRepository;

    @Mock
    private IRoleEntityMapper roleEntityMapper;

    @InjectMocks
    private RoleJpaAdapter adapter;

    private Role role;
    private RoleEntity entity;

    @BeforeEach
    void setUp() {
        role = new Role();
        role.setId(1);
        role.setName("Admin");
        role.setDescription("Role administrator");

        entity = new RoleEntity();
        entity.setId(1);
        entity.setName("Admin");
        entity.setDescription("Role administrator");
    }

    @Test
    void shouldSaveRole() {
        when(roleEntityMapper.toEntity(role)).thenReturn(entity);

        adapter.saveRole(role);

        verify(roleEntityMapper).toEntity(role);
        verify(roleRepository).save(entity);
    }

    @Test
    void shouldGetRoleByIdSuccessfully() {
        when(roleRepository.findById(1)).thenReturn(Optional.of(entity));
        when(roleEntityMapper.toRoleModel(entity)).thenReturn(role);

        Role result = adapter.getRoleById(1);

        assertEquals(role, result);
        verify(roleRepository).findById(1);
        verify(roleEntityMapper).toRoleModel(entity);
    }

    @Test
    void getRoleByIdThrowsWhenNotFound() {
        when(roleRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(NoDataFoundException.class, () -> adapter.getRoleById(1));
        verify(roleRepository).findById(1);
        verify(roleEntityMapper, never()).toRoleModel(any());
    }

    @Test
    void shouldGetAllRolesSuccessfully() {
        List<RoleEntity> entities = Arrays.asList(entity);
        List<Role> domains = Arrays.asList(role);

        when(roleRepository.findAll()).thenReturn(entities);
        when(roleEntityMapper.toRoleModelList(entities)).thenReturn(domains);

        List<Role> result = adapter.getAllRoles();

        assertEquals(domains, result);
        verify(roleRepository).findAll();
        verify(roleEntityMapper).toRoleModelList(entities);
    }

    @Test
    void getAllRolesThrowsWhenEmpty() {
        when(roleRepository.findAll()).thenReturn(Collections.emptyList());

        assertThrows(NoDataFoundException.class, () -> adapter.getAllRoles());
        verify(roleRepository).findAll();
        verify(roleEntityMapper, never()).toRoleModelList(anyList());
    }

    @Test
    void shouldDeleteRoleById() {
        adapter.deleteRoleById(1);

        verify(roleRepository).deleteById(1);
    }
}