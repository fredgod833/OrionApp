package com.mddcore.usecases.auth;

public record AuthResponse(
        Long id,
        String token,
        String picture
) {
}