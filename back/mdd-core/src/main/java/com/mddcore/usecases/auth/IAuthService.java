package com.mddcore.usecases.auth;

import com.mddcore.usecases.request.SignInRequest;
import com.mddcore.usecases.response.JwtResponse;
import com.mddcore.usecases.dto.UserDto;

public interface IAuthService {
    JwtResponse login(SignInRequest request);
    JwtResponse register(UserDto user);
}
