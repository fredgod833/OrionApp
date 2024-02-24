package com.mddinfrastructure.security.jwt;

import com.mddcore.domain.models.Identity;
import com.mddcore.usecases.auth.securityAuth.IJwtExecFinal;
import com.mddcore.usecases.request.SignInRequest;
import com.mddcore.usecases.response.AuthResponse;
import com.mddinfrastructure.security.userdetails.CustomUserDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class JwtExecImpl implements IJwtExecFinal {

    private final Logger logger = LoggerFactory.getLogger(JwtExecImpl.class);
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    public JwtExecImpl(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public AuthResponse jwtToken(SignInRequest signInRequest) {
        logger.info("Sign in request : {}", signInRequest);
        try {
            logger.info("Attempting to authenticate user: {}", signInRequest.email());
            try {
                Authentication authenticate = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(signInRequest.email(), signInRequest.password())
                );
                logger.info("Authentication successful for user: {}", signInRequest.email());
            CustomUserDetails userDetails = (CustomUserDetails) authenticate.getPrincipal();
            logger.info("Before token created");
            String jwt = jwtTokenProvider.createToken(authenticate);
            logger.info("After token created");
            return new AuthResponse(userDetails.getUser().getId().getNumber(), jwt, userDetails.getPictureUrl());
            } catch (BadCredentialsException e) {
                logger.error("Authentication failed for user: {}. Reason: Invalid credentials", signInRequest.email());
            } catch (Exception e) {
                logger.error("Authentication failed for user: {}. Reason: {}", signInRequest.email(), e.getMessage());
            }
        } catch (IllegalArgumentException e) {
            throw  new IllegalArgumentException("Authentication failed for email : " + e);
        }
        return null;
    }

    @Override
    public Identity getAuthenticateUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new IllegalStateException("Authentication object is null. User is not authenticated.");
        }

        Object principal = authentication.getPrincipal();
        if (principal instanceof CustomUserDetails) {
            return ((CustomUserDetails) principal).getUser().getId();
        } else {
            logger.warn("Principal is not an instance of CustomUserDetails. Actual class: {}", principal.getClass());
            throw new IllegalStateException("Principal is not a valid user.");
        }
    }
}
