package com.mddcore.usecases.auth;

import com.mddcore.usecases.request.LoginRequest;
import com.mddcore.usecases.response.JwtResponse;
import com.mddcore.usecases.dto.UserDto;

public interface IAuthService {
    JwtResponse login(LoginRequest request);
    JwtResponse register(UserDto user);
}
