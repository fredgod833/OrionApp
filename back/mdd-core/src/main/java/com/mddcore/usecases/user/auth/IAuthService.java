package com.mddcore.usecases.user.auth;

import com.mddcore.usecases.request.LoginRequest;
import com.mddcore.usecases.response.JwtResponse;
import com.mddcore.usecases.user.dto.UserDto;

public interface IAuthService {

    JwtResponse login(LoginRequest request);
    JwtResponse register(UserDto user);
}
