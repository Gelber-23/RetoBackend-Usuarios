package com.course.users.infraestructure.output.jpa.Adapter;

import com.course.users.domain.model.User;
import com.course.users.domain.spi.IUserPersistencePort;
import com.course.users.infraestructure.exception.NoDataFoundException;
import com.course.users.infraestructure.output.jpa.entity.UserEntity;
import com.course.users.infraestructure.output.jpa.mapper.IUserEntityMapper;
import com.course.users.infraestructure.output.jpa.repository.IUserRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class UserJpaAdapter implements IUserPersistencePort {

    private final IUserRepository userRepository;
    private  final IUserEntityMapper userEntityMapper;

    @Override
    public void saveUser(User user) {

        userRepository.save(userEntityMapper.toEntity(user));
    }

    @Override
    public User getUserById(Long id) {
        return userEntityMapper.toUserModel(userRepository.findById(id)
                .orElseThrow(NoDataFoundException::new));
    }

    @Override
    public List<User> getAllUsers() {
        List<UserEntity> userEntityList = userRepository.findAll();
        if (userEntityList.isEmpty()) {
            throw new NoDataFoundException();
        }
        return userEntityMapper.toUserModelList(userEntityList);
    }

    @Override
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }
}
