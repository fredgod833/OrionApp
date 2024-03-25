package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.security.JWTGenerator;
import com.openclassrooms.mddapi.services.implementation.AuthServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class AuthServiceImplTests {

    private AuthenticationManager authenticationManager;
    private JWTGenerator jwtGenerator;
    private AuthService authService;

    @BeforeEach
    public void setUp() {
        authenticationManager = mock(AuthenticationManager.class);
        jwtGenerator = mock(JWTGenerator.class);
        authService = new AuthServiceImpl(authenticationManager, jwtGenerator);
    }

    @Test
    void generateToken() {
        String email = "test@example.com";
        String token = "mockedToken";

        when(jwtGenerator.generateToken(email)).thenReturn(token);

        String generatedToken = authService.generateToken(email);

        assertEquals(token, generatedToken);
        verify(jwtGenerator).generateToken(email);
        verify(jwtGenerator, times(1)).generateToken(email);
    }

    @Test
    public void testAuthenticate_Success() {
        String email = "user@example.com";
        String password = "Aa12345.";

        authService.authenticate(email, password);

        verify(authenticationManager, times(1))
                .authenticate(new UsernamePasswordAuthenticationToken(email, password));
    }

    @Test
    public void testAuthenticate_Failure() {
        String email = "invalid@example.com";
        String password = "wrongPassword";

        doThrow(BadCredentialsException.class).when(authenticationManager)
                .authenticate(new UsernamePasswordAuthenticationToken(email, password));

        assertThrows(BadCredentialsException.class, () -> authService.authenticate(email, password));
        verify(authenticationManager, times(1))
                .authenticate(new UsernamePasswordAuthenticationToken(email, password));
    }
}
