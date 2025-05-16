package com.course.users.infraestructure.output.jpa.repository;

import com.course.users.infraestructure.output.jpa.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserRepository extends JpaRepository<UserEntity, Long> {
}
