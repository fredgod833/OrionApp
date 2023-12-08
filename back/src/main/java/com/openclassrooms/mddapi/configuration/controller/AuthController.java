package com.openclassrooms.mddapi.configuration.controller;

import com.openclassrooms.mddapi.configuration.AuthService;
import com.openclassrooms.mddapi.configuration.model.Token;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.model.dto.UserDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;

//Authentication api
@RestController
@RequestMapping("api/auth")
public class AuthController {

    //Authentication service layer import
    private final AuthService authService;
    public AuthController(AuthService authService){
        this.authService = authService;
    }

    //Authenticate user
    @PostMapping(path = "/login")
    public Token login(@RequestBody User user) {
        return authService.login(user);
    }

    //User logout
    @PostMapping(path = "/logout")
    public void logout(HttpServletResponse response){
        authService.logout(response);
    }

    //User inscription
    @PostMapping(path = "/register")
    public User register(@RequestBody User user){
        return authService.register(user);
    }

    //User information
    @GetMapping("/me")
    public ResponseEntity<UserDto> getMe(){
        return new ResponseEntity<>(authService.getMe(), HttpStatus.OK);
    }
}
