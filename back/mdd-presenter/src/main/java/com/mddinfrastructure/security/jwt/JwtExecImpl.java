package com.mddinfrastructure.security.jwt;

import com.mddcore.usecases.auth.AuthResponse;
import com.mddcore.usecases.auth.IJwtExecFinal;
import com.mddcore.usecases.auth.SignInRequest;
import com.mddinfrastructure.security.userdetails.CustomUserDetails;
import com.mddinfrastructure.security.userdetails.CustomUserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtExecImpl implements IJwtExecFinal {

    private final Logger logger = LoggerFactory.getLogger(JwtExecImpl.class);
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final CustomUserDetailsService customUserDetailsService;


    public JwtExecImpl(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider,
                       CustomUserDetailsService customUserDetailsService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.customUserDetailsService = customUserDetailsService;
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
            String jwt = jwtTokenProvider.createToken(userDetails);
            logger.info("After token created");
            return new AuthResponse(userDetails.getUser().getId(), jwt, null, userDetails.getPictureUrl());
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
    public Map<String, Object> refreshToken(Long id) {
        CustomUserDetails userDetails = (CustomUserDetails) customUserDetailsService.loadUserById(id);

        String newAccessToken = jwtTokenProvider.createToken(userDetails);
        String newRefreshToken = jwtTokenProvider.createRefreshToken(userDetails);

        Date refreshExpirationDate = jwtTokenProvider.getExpirationDate(newRefreshToken);

        HashMap<String, String> tokens = new HashMap<>();
        tokens.put("accessToken", newAccessToken);
        tokens.put("refreshToken", newRefreshToken);

        tokens.put("refreshTokenExpiration", refreshExpirationDate.getTime());

        return tokens;
    }


    @Override
    public Long getAuthenticateUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new IllegalStateException("Authentication object is null. User is not authenticated.");
        }

        Object principal = authentication.getPrincipal();
        if (principal instanceof CustomUserDetails) {
            return ((CustomUserDetails) principal).getUser().getId();
        } else {
            throw new IllegalStateException("Principal is not a valid user.");
        }
    }
}
