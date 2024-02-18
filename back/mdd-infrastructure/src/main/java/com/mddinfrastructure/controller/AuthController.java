package controller;

import com.openclassrooms.mddapi.common.request.LoginRequest;
import com.openclassrooms.mddapi.common.response.JwtResponse;
import com.openclassrooms.mddapi.core.usecases.user.auth.IAuthService;
import com.openclassrooms.mddapi.core.usecases.user.dto.UserDto;
import com.openclassrooms.mddapi.infrastructure.mapper.RegisterMapper;
import com.openclassrooms.mddapi.infrastructure.request.RegisterRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/auth")
public class AuthController {
    private final IAuthService authService;
    private final RegisterMapper registerMapper;

    public AuthController(IAuthService authService, RegisterMapper registerMapper) {
        this.authService = authService;
        this.registerMapper = registerMapper;
    }

    @PostMapping("/register")
    private ResponseEntity<JwtResponse> register(@RequestBody RegisterRequest registerRequest) {
        UserDto user = registerMapper.toDto(registerRequest);
        return ResponseEntity.ok(authService.register(user));
    }

    @PostMapping("/login")
    private ResponseEntity<JwtResponse> login(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.login(loginRequest));
    }
}

