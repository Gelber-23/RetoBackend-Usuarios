package com.course.users.infraestructure.configuration;

import com.course.users.domain.api.IRoleServicePort;
import com.course.users.domain.api.IUserServicePort;
import com.course.users.domain.spi.IRolePersistencePort;
import com.course.users.domain.spi.IUserPersistencePort;
import com.course.users.domain.spi.IUtilsPort;
import com.course.users.domain.usercase.RoleUseCase;
import com.course.users.domain.usercase.UserUseCase;
import com.course.users.infraestructure.output.jpa.Adapter.RoleJpaAdapter;
import com.course.users.infraestructure.output.jpa.Adapter.UserJpaAdapter;
import com.course.users.infraestructure.output.jpa.mapper.IRoleEntityMapper;
import com.course.users.infraestructure.output.jpa.mapper.IUserEntityMapper;
import com.course.users.infraestructure.output.jpa.repository.IRoleRepository;
import com.course.users.infraestructure.output.jpa.repository.IUserRepository;
import com.course.users.infraestructure.utils.UtilsAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {

    private final IUserRepository userRepository;
    private final IUserEntityMapper userEntityMapper;

    private final IRoleRepository roleRepository;
    private final IRoleEntityMapper roleEntityMapper;
    private final IUtilsPort utilsPort;


    @Bean
    public IUserPersistencePort userPersistencePort(){
        return new UserJpaAdapter(userRepository, userEntityMapper);
    }


    @Bean
    public IUserServicePort userServicePort(){
        return new UserUseCase(userPersistencePort(),utilsPort);
    }

    @Bean
    public IRolePersistencePort rolePersistencePort(){
        return new RoleJpaAdapter(roleRepository, roleEntityMapper);
    }

    @Bean
    public IRoleServicePort roleServicePort(){
        return new RoleUseCase(rolePersistencePort());
    }
}
