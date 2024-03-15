package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.dtos.LoginDTO;
import com.openclassrooms.mddapi.dtos.RegisterDTO;
import com.openclassrooms.mddapi.responses.AuthResponse;
import com.openclassrooms.mddapi.responses.MessageResponse;
import com.openclassrooms.mddapi.services.AuthService;
import com.openclassrooms.mddapi.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final AuthService authService;

    @Autowired
    public AuthController(UserService userService, AuthService authService) {
        this.userService = userService;
        this.authService = authService;
    }

    @PostMapping("/register")
    @Operation(summary = "Register a new user", description = "Registers a new user and returns a JWT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User registration successful",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AuthResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad request - Email already exists", content = @Content)})
    public ResponseEntity<?> register(@RequestBody RegisterDTO registerDTO, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }
        try {
            userService.register(registerDTO);
        } catch (DuplicateKeyException ex) {
            return ResponseEntity.badRequest().body(new MessageResponse(ex.getMessage()));
        }

        return ResponseEntity.ok(new AuthResponse(authService.generateToken(registerDTO.getEmail())));
    }

    @PostMapping("/login")
    @Operation(summary = "Authentication", description = "Authenticates a user and returns a JWT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login successful",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AuthResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)})
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDto, Errors errors) {
        if (errors.hasErrors()) {
            return new ResponseEntity<>(new MessageResponse("error"), HttpStatus.UNAUTHORIZED);
        }
        try {
            authService.authenticate(loginDto.getEmail(), loginDto.getPassword());
        } catch (BadCredentialsException ex) {
            return new ResponseEntity<>(new MessageResponse("Bad credentials"), HttpStatus.UNAUTHORIZED);
        }

        return ResponseEntity.ok(new AuthResponse(authService.generateToken(loginDto.getEmail())));
    }
}
