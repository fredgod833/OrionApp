package com.mddinfrastructure.config;

import com.mddcore.domain.repository.IUserRepository;
import com.mddcore.usecases.auth.securityAuth.IJwtExecFinal;
import com.mddcore.usecases.auth.securityAuth.IPasswordEncodeFinal;
import com.mddcore.usecases.mapper.UserMapper;
import com.mddcore.usecases.user.*;
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
    public GetUserUseCase getUserUseCase(IUserRepository userRepository) {
        return new GetUserUseCase(userRepository);
    }

    @Bean
    public DeleteUserUseCase deleteUserUseCase(IUserRepository userRepository, IJwtExecFinal jwtExecFinal) {
        return new DeleteUserUseCase(userRepository, jwtExecFinal);
    }

    @Bean
    public UpdateUserUseCase updateUserUseCase(IUserRepository userRepository, IPasswordEncodeFinal passwordEncodeFinal, IJwtExecFinal jwtExecFinal) {
        return new UpdateUserUseCase(userRepository, passwordEncodeFinal, jwtExecFinal);
    }

    @Bean
    public RegisterUseCase registerUseCase(IUserRepository userRepository, IPasswordEncodeFinal passwordEncodeFinal) {
        return new RegisterUseCase(userRepository, passwordEncodeFinal);
    }



    @Bean
    public LoginUseCase loginUseCase(IJwtExecFinal jwtExecFinal) {
        return new LoginUseCase(jwtExecFinal);
    }
}
