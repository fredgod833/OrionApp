package com.mddcore.usecases.auth;


public interface IJwtExecFinal {
    AuthResponse jwtToken(SignInRequest request);
    Long getAuthenticateUser();
}
