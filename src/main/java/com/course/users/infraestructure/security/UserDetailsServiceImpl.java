package com.course.users.infraestructure.security;

import com.course.users.domain.model.User;
import com.course.users.infraestructure.output.jpa.entity.UserEntity;
import com.course.users.infraestructure.output.jpa.mapper.IUserEntityMapper;
import com.course.users.infraestructure.output.jpa.repository.IUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final IUserRepository userRepository;
    private final IUserEntityMapper userEntityMapper;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
       UserEntity userEntity=  userRepository
                .findOneByEmail(email)
                .orElseThrow(()->new UsernameNotFoundException("The user with email:  "+email+" does not exist."));

        return new UserDetailsImpl( userEntityMapper.toUserModel(userEntity));
    }
}
