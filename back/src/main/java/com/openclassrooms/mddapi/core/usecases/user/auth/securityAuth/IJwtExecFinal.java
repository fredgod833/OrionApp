package com.openclassrooms.mddapi.core.usecases.user.auth.securityAuth;

import com.openclassrooms.mddapi.common.request.LoginRequest;

public interface IJwtExecFinal {
    String jwtToken(LoginRequest request);
}
