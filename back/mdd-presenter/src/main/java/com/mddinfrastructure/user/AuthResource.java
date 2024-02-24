package com.mddinfrastructure.user;

import com.mddcore.usecases.request.SignInRequest;
import com.mddcore.usecases.response.AuthResponse;
import com.mddinfrastructure.request.UserSettingRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/auth")
public interface AuthResource {
    @PostMapping("/register")
    CompletableFuture<SignInRequest> registerUser(@RequestBody UserSettingRequest userSettingRequest);

    @PostMapping("/login")
    CompletableFuture<AuthResponse> loginUser(@RequestBody SignInRequest signInRequest);
}
