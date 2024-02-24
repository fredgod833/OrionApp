package com.mddinfrastructure.user;

import com.mddinfrastructure.request.UserSettingRequest;
import com.mddinfrastructure.response.ApiResponse;
import com.mddinfrastructure.response.UserResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/user")
public interface UserResource {
    @GetMapping("/{id}")
    CompletableFuture<UserResponse> getUserById(@PathVariable Long id);

    @DeleteMapping("/{id}")
    CompletableFuture<ResponseEntity<ApiResponse>> deleteUserById(@PathVariable Long id);

    @PutMapping("/{id}")
    CompletableFuture<UserResponse> updateUser(@PathVariable Long id, @RequestBody UserSettingRequest userSettingRequest);

    @GetMapping()
    CompletableFuture<ResponseEntity<?>> getUserAuth();
}
