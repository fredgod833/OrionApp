package com.mddinfrastructure.user;

import com.mddcore.usecases.auth.SignInRequest;
import com.mddinfrastructure.request.UserSettingRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/auth")
public interface AuthResource {
    @PostMapping("/register")
    CompletableFuture<SignInRequest> registerUser(@RequestBody UserSettingRequest userSettingRequest);

    @PostMapping("/login")
    CompletableFuture<ResponseEntity<?>> loginUser(@RequestBody SignInRequest signInRequest);

    @PostMapping("/signout")
    CompletableFuture<ResponseEntity<?>> logoutUser();

    @PostMapping("/refresh-token")
    CompletableFuture<ResponseEntity<?>> refreshToken(HttpServletRequest request);


}
