package com.mddcore.usecases.auth;


import java.util.Map;

public interface IJwtExecFinal {
    AuthResponse jwtToken(SignInRequest request);
    Long getAuthenticateUser();
    Map<String, Object> refreshToken(Long userId);
}
