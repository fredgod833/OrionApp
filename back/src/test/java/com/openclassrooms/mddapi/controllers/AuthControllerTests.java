package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.payload.requests.LoginRequest;
import com.openclassrooms.mddapi.payload.requests.RegisterRequest;
import com.openclassrooms.mddapi.payload.responses.AuthResponse;
import com.openclassrooms.mddapi.payload.responses.MessageResponse;
import com.openclassrooms.mddapi.services.AuthService;
import com.openclassrooms.mddapi.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.Errors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class AuthControllerTests {

    private AuthController controller;
    private UserService userService;
    private AuthService authService;
    private Errors errors;

    @BeforeEach
    public void setUp() {
        errors = mock(Errors.class);
        userService = mock(UserService.class);
        authService = mock(AuthService.class);
        controller = new AuthController(userService, authService);
    }

    @Test
    public void testRegister_Success() {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("test_user");
        registerRequest.setEmail("test@example.com");
        registerRequest.setPassword("password");
        when(authService.generateToken(registerRequest.getEmail())).thenReturn("generated_token");

        ResponseEntity<?> response = controller.register(registerRequest, errors);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("generated_token", ((AuthResponse) response.getBody()).getToken());
    }

    @Test
    public void testRegister_DuplicateKeyException() {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("test_user");
        registerRequest.setEmail("test@example.com");
        registerRequest.setPassword("password");
        DuplicateKeyException duplicateKeyException = new DuplicateKeyException("Email already exists");
        doThrow(duplicateKeyException).when(userService).register(registerRequest);

        ResponseEntity<?> response = controller.register(registerRequest, errors);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Email already exists", ((MessageResponse) response.getBody()).getMessage());
    }

    @Test
    public void testLogin_Success() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("test@test.gmail");
        loginRequest.setPassword("password");

        when(authService.generateToken(loginRequest.getEmail())).thenReturn("mocked-token");

        ResponseEntity<?> response = controller.login(loginRequest, errors);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("mocked-token", ((AuthResponse) response.getBody()).getToken());
    }

    @Test
    public void testLogin_BadCredentials() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("invalid@test.gmail");
        loginRequest.setPassword("invalid");

        doThrow(BadCredentialsException.class)
                .when(authService).authenticate(loginRequest.getEmail(), loginRequest.getPassword());

        ResponseEntity<?> response = controller.login(loginRequest, errors);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Bad credentials", ((MessageResponse) response.getBody()).getMessage());
    }

    @Test
    public void testLogin_ValidationErrors() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail(null);
        loginRequest.setPassword(null);

        Errors errors = mock(Errors.class);
        when(errors.hasErrors()).thenReturn(true);

        ResponseEntity<?> response = controller.login(loginRequest, errors);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("error", ((MessageResponse) response.getBody()).getMessage());
    }
}
