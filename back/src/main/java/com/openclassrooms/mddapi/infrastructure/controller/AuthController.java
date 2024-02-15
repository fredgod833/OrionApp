package com.openclassrooms.mddapi.infrastructure.controller;

import com.openclassrooms.mddapi.common.request.LoginRequest;
import com.openclassrooms.mddapi.core.usecases.user.auth.IAuthService;
import com.openclassrooms.mddapi.core.usecases.user.dto.UserDto;
import com.openclassrooms.mddapi.infrastructure.mapper.RegisterMapper;
import com.openclassrooms.mddapi.infrastructure.request.RegisterRequest;
import com.openclassrooms.mddapi.infrastructure.security.IJwtExec;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/user/auth")
public class AuthController {

    private final IAuthService authService;
    private final RegisterMapper registerMapper;

    private final IJwtExec jwtExec;

    public AuthController(IAuthService authService, RegisterMapper registerMapper, IJwtExec jwtExec) {
        this.authService = authService;
        this.registerMapper = registerMapper;
        this.jwtExec = jwtExec;
    }

    @PostMapping("/register")
    private ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
        UserDto user = registerMapper.toDto(registerRequest);
        authService.register(user);
        String token = jwtExec.makeToken(registerRequest.getEmail(), registerRequest.getPassword());
        return ResponseEntity.ok(Map.of("token", token));
    }

    @PostMapping("/login")
    private ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {

    }
}

