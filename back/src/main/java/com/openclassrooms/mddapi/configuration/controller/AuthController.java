package com.openclassrooms.mddapi.configuration.controller;

import com.openclassrooms.mddapi.configuration.AuthService;
import com.openclassrooms.mddapi.configuration.model.LoginRequest;
import com.openclassrooms.mddapi.configuration.model.Token;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.attribute.UserPrincipalNotFoundException;

@RestController
@RequestMapping("api/auth")
public class AuthController {
    private final AuthService authService;
    public AuthController(AuthService authService){
        this.authService = authService;
    }
    @PostMapping(path = "/login")
    public Token login(@RequestBody User user) throws UserPrincipalNotFoundException {
        return authService.login(user);
    }

    @PostMapping(path = "/register")
    public User register(@RequestBody User user){
        return authService.register(user);
    }
}
