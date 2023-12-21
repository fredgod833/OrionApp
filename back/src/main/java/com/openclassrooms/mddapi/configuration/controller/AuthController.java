package com.openclassrooms.mddapi.configuration.controller;

import com.openclassrooms.mddapi.configuration.AuthService;
import com.openclassrooms.mddapi.configuration.model.Token;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.model.dto.UserDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import java.nio.file.attribute.UserPrincipalNotFoundException;

/**
 * This class authenticate users in order to allow access to the application
 */
@RestController
@RequestMapping("api/auth")
public class AuthController {

    /**
     * Authenticate service layer import
     */
    private final AuthService authService;
    public AuthController(AuthService authService){
        this.authService = authService;
    }

    /**
     * Allow authenticated users to access application
     * @param user should contain right credentials
     * @return a token to validate access
     */
    @PostMapping(path = "/login")
    public Token login(@RequestBody User user) {
        return authService.login(user);
    }

    /**
     * Allow users to logout
     * @param response
     */
    @PostMapping(path = "/logout")
    public void logout(HttpServletResponse response){
        authService.logout(response);
    }

    /**
     * Create a new user with authentication
     * @param user requires personal user information
     * @return new user authenticated
     */
    @PostMapping(path = "/register")
    public User register(@RequestBody User user) throws UserPrincipalNotFoundException {
        return authService.register(user);
    }

    /**
     * Allow authenticated user to get its information
     * @return user information
     */
    @GetMapping("/me")
    public ResponseEntity<UserDto> getMe(){
        return new ResponseEntity<>(authService.getMe(), HttpStatus.OK);
    }
}
