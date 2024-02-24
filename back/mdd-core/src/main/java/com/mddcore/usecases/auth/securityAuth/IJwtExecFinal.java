package com.mddcore.usecases.auth.securityAuth;


import com.mddcore.domain.models.Identity;
import com.mddcore.usecases.request.SignInRequest;
import com.mddcore.usecases.response.AuthResponse;

public interface IJwtExecFinal {
    AuthResponse jwtToken(SignInRequest request);
    Identity getAuthenticateUser();
}
