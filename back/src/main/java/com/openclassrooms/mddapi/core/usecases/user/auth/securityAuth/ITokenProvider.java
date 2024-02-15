package com.openclassrooms.mddapi.core.usecases.user.auth.securityAuth;

import com.openclassrooms.mddapi.common.request.LoginRequest;

import java.util.Collection;

public interface ITokenProvider {
    String createToken(String username, Collection<? extends IGrantedAuthority> authorities);
    String extractToken(LoginRequest request);
    boolean validateToken(String token);
    String getUsernameFromToken(String token);
}
