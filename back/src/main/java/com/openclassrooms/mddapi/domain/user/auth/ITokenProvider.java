package com.openclassrooms.mddapi.domain.user.auth;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

public interface ITokenProvider {
    String createToken(String username, Collection<? extends IGrantedAuthority> authorities);
    String extractToken(HttpServletRequest request);
    boolean validateToken(String token);
    String getUsernameFromToken(String token);
}
