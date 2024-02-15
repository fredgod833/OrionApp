package com.openclassrooms.mddapi.infrastructure.security;

import com.openclassrooms.mddapi.common.request.LoginRequest;
import com.openclassrooms.mddapi.core.usecases.user.auth.securityAuth.IJwtExecFinal;

public class JwtExecImpl implements IJwtExecFinal {

    private final IJwtExec iJwtExec;

    public JwtExecImpl(IJwtExec iJwtExec) {
        this.iJwtExec = iJwtExec;
    }

    @Override
    public String jwtToken(LoginRequest loginRequest) {
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();
        return iJwtExec.makeToken(email, password);
    }

}
