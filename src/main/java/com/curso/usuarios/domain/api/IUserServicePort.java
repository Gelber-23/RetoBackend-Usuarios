package com.curso.usuarios.domain.api;

import com.curso.usuarios.domain.model.User;

import java.util.List;

public interface IUserServicePort {

    void saveUser(User user);

    User getUserById(Long id);

    List<User> getAllUsers();

    void deleteUserById(Long id);
}
