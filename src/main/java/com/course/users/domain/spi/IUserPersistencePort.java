package com.course.users.domain.spi;

import com.course.users.domain.model.User;

import java.util.List;

public interface IUserPersistencePort {

    void saveUser(User user);

    User getUserById(Long id);

    List<User> getAllUsers();

    void deleteUserById(Long id);
}
