package com.course.users.infraestructure.configuration;

import com.course.users.domain.spi.IUtilsPort;
import com.course.users.domain.usercase.RoleUseCase;
import com.course.users.domain.usercase.UserUseCase;
import com.course.users.infraestructure.output.jpa.Adapter.RoleJpaAdapter;
import com.course.users.infraestructure.output.jpa.Adapter.UserJpaAdapter;
import com.course.users.infraestructure.output.jpa.mapper.IRoleEntityMapper;
import com.course.users.infraestructure.output.jpa.mapper.IUserEntityMapper;
import com.course.users.infraestructure.output.jpa.repository.IRoleRepository;
import com.course.users.infraestructure.output.jpa.repository.IUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class BeanConfigurationTest {
    @Mock
    IUserRepository userRepository;
    @Mock
    IUserEntityMapper userEntityMapper;
    @Mock
    IRoleRepository roleRepository;
    @Mock
    IRoleEntityMapper roleEntityMapper;
    @Mock
    IUtilsPort utilsPort;
    @InjectMocks
    BeanConfiguration config;



    @Test
    void userPersistencePort_shouldReturnUserJpaAdapter_withInjectedDeps() throws Exception {
        Object bean = config.userPersistencePort();
        assertInstanceOf(UserJpaAdapter.class, bean);

        Field repoField = UserJpaAdapter.class.getDeclaredField("userRepository");
        repoField.setAccessible(true);
        assertSame(userRepository, repoField.get(bean));

        Field mapperField = UserJpaAdapter.class.getDeclaredField("userEntityMapper");
        mapperField.setAccessible(true);
        assertSame(userEntityMapper, mapperField.get(bean));
    }

    @Test
    void userServicePort_shouldReturnUserUseCase_withCorrectDeps() throws Exception {
        Object bean = config.userServicePort();
        assertInstanceOf(UserUseCase.class, bean);


        Field persistenceField = UserUseCase.class.getDeclaredField("userPersistencePort");
        persistenceField.setAccessible(true);
        Object persistenceBean = persistenceField.get(bean);
        assertInstanceOf(UserJpaAdapter.class, persistenceBean);


        Field utilsField = UserUseCase.class.getDeclaredField("utilsPort");
        utilsField.setAccessible(true);
        assertSame(utilsPort, utilsField.get(bean));
    }

    @Test
    void rolePersistencePort_shouldReturnRoleJpaAdapter_withInjectedDeps() throws Exception {
        Object bean = config.rolePersistencePort();
        assertInstanceOf(RoleJpaAdapter.class, bean);

        Field repoField = RoleJpaAdapter.class.getDeclaredField("roleRepository");
        repoField.setAccessible(true);
        assertSame(roleRepository, repoField.get(bean));

        Field mapperField = RoleJpaAdapter.class.getDeclaredField("roleEntityMapper");
        mapperField.setAccessible(true);
        assertSame(roleEntityMapper, mapperField.get(bean));
    }

    @Test
    void roleServicePort_shouldReturnRoleUseCase_withCorrectDeps() throws Exception {
        Object bean = config.roleServicePort();
        assertInstanceOf(RoleUseCase.class, bean);

        Field persistenceField = RoleUseCase.class.getDeclaredField("rolePersistencePort");
        persistenceField.setAccessible(true);
        Object persistenceBean = persistenceField.get(bean);
        assertInstanceOf(RoleJpaAdapter.class, persistenceBean);
    }

}