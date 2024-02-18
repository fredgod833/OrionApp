package com.mddcore.usecases.user.auth;

import com.mddcore.domain.models.User;
import com.mddcore.domain.repository.IUserRepository;
import com.mddcore.usecases.request.LoginRequest;
import com.mddcore.usecases.response.JwtResponse;
import com.mddcore.usecases.user.UserMapper;
import com.mddcore.usecases.user.auth.securityAuth.IJwtExecFinal;
import com.mddcore.usecases.user.auth.securityAuth.IPasswordEncodeFinal;
import com.mddcore.usecases.user.dto.UserDto;

public class AuthServiceImpl implements IAuthService{
    private final IJwtExecFinal jwtExecFinal;
    private final IUserRepository userRepository;
    private final UserMapper userMapper;
    private final IPasswordEncodeFinal passwordEncodeFinal;

    public AuthServiceImpl(IJwtExecFinal jwtExecFinal, IUserRepository userRepository,
                           UserMapper userMapper, IPasswordEncodeFinal passwordEncodeFinal) {
        this.jwtExecFinal = jwtExecFinal;
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncodeFinal = passwordEncodeFinal;
    }

    @Override
    public JwtResponse login(LoginRequest request) {
        try {
            return jwtExecFinal.jwtToken(request);
        } catch (Exception e) {
            throw new IllegalStateException("An unexpected error occurred during login", e);
        }
    }

    @Override
    public JwtResponse register(UserDto userDto) {
        if(userRepository.existByEmail(userDto.getEmail())) {
            throw new IllegalArgumentException("User email already exist");
        };

        if(!validPassword(userDto.getPassword())) {
            throw new IllegalArgumentException("Password security check failed");
        };

        User user = userMapper.toEntity(userDto);

        String passwordEnc = passwordEncodeFinal.passwordEnc(user.getPassword());
        user.setPassword(passwordEnc);
        userRepository.save(user);

        LoginRequest loginRequest = new LoginRequest(user.getEmail(), user.getPassword());

        return jwtExecFinal.jwtToken(loginRequest);
    }

    private Boolean validPassword(String password) {
        return password.length() >= 8
                && password.matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*[^a-zA-Z0-9]).*$");
    }
}
