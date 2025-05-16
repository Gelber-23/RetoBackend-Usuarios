package com.course.users.infraestructure.output.jpa.repository;

import com.course.users.infraestructure.output.jpa.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRoleRepository extends JpaRepository<RoleEntity, Integer> {
}
