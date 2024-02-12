package com.openclassrooms.mddapi.application.user.auth;

import com.openclassrooms.mddapi.application.user.auth.request.LoginRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

public interface ITokenProvider {
    String createToken(String username, Collection<? extends IGrantedAuthority> authorities);
    String extractToken(LoginRequest request);
    boolean validateToken(String token);
    String getUsernameFromToken(String token);
}
