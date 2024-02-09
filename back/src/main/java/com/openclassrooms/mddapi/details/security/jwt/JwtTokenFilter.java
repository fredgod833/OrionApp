package com.openclassrooms.mddapi.details.security.jwt;

import com.tonight.back.infrastructure.auth.CustomUserDetailsService;
import com.tonight.back.infrastructure.auth.JwtTokenProvider;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class JwtTokenFilter {
    private JwtTokenProvider jwtTokenProvider;
    private CustomUserDetailsService customUserDetailsService;
}
