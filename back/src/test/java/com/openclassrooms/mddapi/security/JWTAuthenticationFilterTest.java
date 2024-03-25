package com.openclassrooms.mddapi.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class JWTAuthenticationFilterTest {

    @InjectMocks
    private JWTAuthenticationFilter filter;

    @Mock
    private JWTGenerator jwtGenerator;

    @Mock
    private UserDetailsServiceImpl userDetailsService;

    @Test
    void testDoFilterInternal_ValidToken() throws ServletException, IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain filterChain = mock(FilterChain.class);
        UserDetails userDetails = mock(UserDetails.class);

        String token = "validToken";
        String email = "test@example.com";

        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
        when(jwtGenerator.validateToken(token)).thenReturn(true);
        when(jwtGenerator.getEmailFromJWT(token)).thenReturn(email);
        when(userDetailsService.loadUserByUsername(email)).thenReturn(userDetails);

        filter.doFilterInternal(request, response, filterChain);

        verify(jwtGenerator, times(1)).validateToken(token);
        verify(jwtGenerator, times(1)).getEmailFromJWT(token);
        verify(userDetailsService, times(1)).loadUserByUsername(email);
        verify(filterChain, times(1)).doFilter(request, response);
        assertEquals(userDetails, SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }
}
