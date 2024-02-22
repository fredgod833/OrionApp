package com.mddinfrastructure.http;

import com.mddcore.usecases.auth.IAuthService;
import com.mddcore.usecases.dto.UserDto;
import com.mddcore.usecases.request.LoginRequest;
import com.mddcore.usecases.response.JwtResponse;
import com.mddinfrastructure.mapper.RegisterMapper;
import com.mddinfrastructure.request.RegisterRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/auth")
public class AuthController {

    private final static Logger LOGGER = LoggerFactory.getLogger(AuthController.class);
    @Autowired
    private IAuthService authService;

    @Autowired
    private RegisterMapper registerMapper;

    @PostMapping("/register")
    private ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
        LOGGER.info("started...");
        UserDto user = registerMapper.toDto(registerRequest);
        LOGGER.info("Before sending response");
        return ResponseEntity.ok(authService.register(user));
    }

    @PostMapping("/login")
    private ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.login(loginRequest));
    }

    @GetMapping("/test")
    public ResponseEntity<?> test() {
        JwtResponse jwtResponse =  JwtResponse.builder().token("1233fdlsdflksdlkfsdfjlsdlfkjsdflksdjflsdkfjsdlkfjsdlkfjsdlfkjsdflkjsdf").email("the@gmail.com").build();
        return ResponseEntity.ok(jwtResponse);
    }
}

