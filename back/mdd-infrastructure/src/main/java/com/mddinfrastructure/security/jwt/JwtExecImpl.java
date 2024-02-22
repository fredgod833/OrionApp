package com.mddinfrastructure.security.jwt;

import com.mddcore.usecases.auth.securityAuth.IJwtExecFinal;
import com.mddcore.usecases.request.LoginRequest;
import com.mddcore.usecases.response.JwtResponse;
import com.mddinfrastructure.security.userdetails.CustomUserDetails;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class JwtExecImpl implements IJwtExecFinal {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    public JwtExecImpl(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public JwtResponse jwtToken(LoginRequest loginRequest) {
        try {
            Authentication authenticate = authenticationManager.authenticate(
                                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
                        );
            CustomUserDetails userDetails = (CustomUserDetails) authenticate.getPrincipal();
            String jwt = jwtTokenProvider.createToken(authenticate);
            return JwtResponse.builder().token(jwt).id(userDetails.getUser().getId()).email(userDetails.getUsername()).username(userDetails.getUser().getUsername()).build();
        } catch (IllegalArgumentException e) {
            throw  new IllegalArgumentException("Authentication failed for email : " + e);
        }
    }

    public String getAuthenticateUser() {
       Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ((CustomUserDetails) authentication.getPrincipal()).getUser().getEmail();
    }
}
