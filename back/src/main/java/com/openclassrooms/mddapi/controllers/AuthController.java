package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.dtos.LoginDTO;
import com.openclassrooms.mddapi.dtos.RegisterDTO;
import com.openclassrooms.mddapi.responses.AuthResponse;
import com.openclassrooms.mddapi.responses.MessageResponse;
import com.openclassrooms.mddapi.services.AuthService;
import com.openclassrooms.mddapi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<?> register(@RequestBody RegisterDTO registerDTO, Errors errors) {
        if (errors.hasErrors() || userService.exists(registerDTO.getEmail())) {
            return ResponseEntity.badRequest().build();
        }
        userService.register(registerDTO);

        return ResponseEntity.ok(new AuthResponse(authService.generateToken(registerDTO.getEmail())));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDto, Errors errors) {
        if (errors.hasErrors()) {
            return new ResponseEntity<>(new MessageResponse("error"), HttpStatus.UNAUTHORIZED);
        }
        try {
            authService.authenticate(loginDto.getEmail(), loginDto.getPassword());
        } catch (BadCredentialsException ex) {
            return new ResponseEntity<>(new MessageResponse("error"), HttpStatus.UNAUTHORIZED);
        }

        return ResponseEntity.ok(new AuthResponse(authService.generateToken(loginDto.getEmail())));
    }
}
