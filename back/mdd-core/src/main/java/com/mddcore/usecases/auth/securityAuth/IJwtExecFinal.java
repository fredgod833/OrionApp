package com.mddcore.usecases.auth.securityAuth;


import com.mddcore.usecases.request.LoginRequest;
import com.mddcore.usecases.response.JwtResponse;

public interface IJwtExecFinal {
    JwtResponse jwtToken(LoginRequest request);
}
