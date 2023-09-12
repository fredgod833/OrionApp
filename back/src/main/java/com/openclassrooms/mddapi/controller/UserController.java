package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.dto.ResponseDto;
import com.openclassrooms.mddapi.dto.UserLoginDto;
import com.openclassrooms.mddapi.dto.UserRegisterDto;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.service.TokenService;
import com.openclassrooms.mddapi.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final ResponseDto responseDto = new ResponseDto();
    public UserController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @PostMapping(value = "/auth/login", produces = { "application/json" })
    public ResponseDto login(@RequestBody UserLoginDto userLoginDto, HttpServletRequest req) {
        String token = tokenService.generateToken(
            userService.userAuthentication(
                userLoginDto.getEmail(),
                userLoginDto.getPassword(),
                req,
                authenticationManager
            )
        );
        responseDto.setResponse(token);
        return responseDto;
    }

    @PostMapping(value = "/auth/register", produces = { "application/json" })
    public ResponseDto register(@RequestBody @Valid UserRegisterDto userRegisterDto) {
        try {
            userService.saveUser(userRegisterDto);
            responseDto.setResponse("success");
            return responseDto;
        } catch (Exception e) {
            responseDto.setResponse("error");
            return responseDto;
        }
    }

    @GetMapping(value = "/auth/me", produces = { "application/json" })
    public ResponseEntity<User> getCurrentUser(Authentication authentication) {
        try {
            User user = userService.getUserByEmail(authentication.getName());
            return new ResponseEntity<User>(user, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
    }
}
