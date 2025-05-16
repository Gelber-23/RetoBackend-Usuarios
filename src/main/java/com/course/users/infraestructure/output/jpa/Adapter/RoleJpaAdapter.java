package com.course.users.infraestructure.output.jpa.Adapter;


import com.course.users.domain.model.Role;
import com.course.users.domain.spi.IRolePersistencePort;
import com.course.users.infraestructure.exception.NoDataFoundException;
import com.course.users.infraestructure.output.jpa.entity.RoleEntity;
import com.course.users.infraestructure.output.jpa.mapper.IRoleEntityMapper;
import com.course.users.infraestructure.output.jpa.repository.IRoleRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class RoleJpaAdapter implements IRolePersistencePort {

    private final IRoleRepository roleRepository;
    private  final IRoleEntityMapper roleEntityMapper;

    @Override
    public void saveRole(Role role) {
        roleRepository.save(roleEntityMapper.toEntity(role));
    }

    @Override
    public Role getRoleById(int id) {
        return roleEntityMapper.toRoleModel(roleRepository.findById(id)
                .orElseThrow(NoDataFoundException::new));
    }

    @Override
    public List<Role> getAllRoles() {
        List<RoleEntity> roleEntityList = roleRepository.findAll();
        if (roleEntityList.isEmpty()) {
            throw new NoDataFoundException();
        }
        return roleEntityMapper.toRoleModelList(roleEntityList);
    }

    @Override
    public void deleteRoleById(int id) {
        roleRepository.deleteById(id);
    }
}
