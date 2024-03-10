package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.dtos.LoginDTO;
import com.openclassrooms.mddapi.dtos.RegisterDTO;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.responses.AuthResponse;
import com.openclassrooms.mddapi.security.JWTGenerator;
import com.openclassrooms.mddapi.security.UserDetailsImpl;
import com.openclassrooms.mddapi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JWTGenerator jwtGenerator;
    private final UserService userService;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, JWTGenerator jwtGenerator, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtGenerator = jwtGenerator;
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterDTO registerDTO) {
        if (userService.userExists(registerDTO.getEmail())) {
            return ResponseEntity.badRequest().body("Email already exists");
        }
        userService.register(registerDTO);

        return authenticateAndGenerateToken(registerDTO.getEmail(), registerDTO.getPassword());
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginDTO loginDto) {
        return authenticateAndGenerateToken(loginDto.getEmail(), loginDto.getPassword());
    }

    @GetMapping("/me")
    public ResponseEntity<?> getUser(Authentication authentication) {
        if (authentication == null) return ResponseEntity.badRequest().build();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return ResponseEntity.ok(new User(
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getName(),
                userDetails.getCreatedAt(),
                userDetails.getUpdatedAt()));
    }

    private ResponseEntity<AuthResponse> authenticateAndGenerateToken(String email, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password));
        String token = jwtGenerator.generateToken(authentication);

        return ResponseEntity.ok(new AuthResponse(token));
    }
}
