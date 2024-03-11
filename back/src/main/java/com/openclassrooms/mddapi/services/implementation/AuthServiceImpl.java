package com.openclassrooms.mddapi.services.implementation;

import com.openclassrooms.mddapi.security.JWTGenerator;
import com.openclassrooms.mddapi.services.AuthService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final JWTGenerator jwtGenerator;

    public AuthServiceImpl(AuthenticationManager authenticationManager,
                           JWTGenerator jwtGenerator) {
        this.authenticationManager = authenticationManager;
        this.jwtGenerator = jwtGenerator;
    }

    public String generateToken(String email) {
        return jwtGenerator.generateToken(email);
    }

    public void authenticate(String email, String password) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password));
    }
}
