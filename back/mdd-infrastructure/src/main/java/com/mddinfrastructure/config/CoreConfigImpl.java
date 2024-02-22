package com.mddinfrastructure.config;

import com.mddcore.domain.repository.IUserRepository;
import com.mddcore.usecases.auth.AuthServiceImpl;
import com.mddcore.usecases.auth.IAuthService;
import com.mddcore.usecases.auth.securityAuth.IJwtExecFinal;
import com.mddcore.usecases.auth.securityAuth.IPasswordEncodeFinal;
import com.mddcore.usecases.iservice.IUserService;
import com.mddcore.usecases.mapper.UserMapper;
import com.mddcore.usecases.service.UserServiceImpl;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CoreConfigImpl {
    @Bean
    public UserMapper userMapper() {
        return Mappers.getMapper(UserMapper.class);
    }

    @Bean
    public IAuthService authService(IJwtExecFinal jwtExecFinal, IUserRepository userRepository, UserMapper userMapper, IPasswordEncodeFinal passwordEncodeFinal) {
        return new AuthServiceImpl(jwtExecFinal, userRepository, userMapper, passwordEncodeFinal);
    }

    @Bean
    public IUserService userService(IUserRepository userRepository, UserMapper userMapper) {
        return new UserServiceImpl(userRepository, userMapper);
    }

}
