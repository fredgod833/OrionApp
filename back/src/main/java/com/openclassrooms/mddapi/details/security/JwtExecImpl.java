package com.openclassrooms.mddapi.details.security;

import com.openclassrooms.mddapi.common.exceptions.BadRequestException;
import com.openclassrooms.mddapi.details.security.jwt.JwtTokenProvider;
import com.openclassrooms.mddapi.infrastructure.security.IJwtExec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
public class JwtExecImpl implements IJwtExec {

    @Autowired
    private final AuthenticationManager authenticationManager;
    @Autowired
    private final JwtTokenProvider jwtTokenProvider;

    public JwtExecImpl(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public String makeToken(String email, String password) {
        try {
        Authentication authenticate = authenticationManager.authenticate(
                            new UsernamePasswordAuthenticationToken(email, password)
                    );
            return jwtTokenProvider.createToken(authenticate);
        } catch (AuthenticationException e) {
            throw  new BadRequestException("Authentication failed for email : " + e);
        }
    }
}
