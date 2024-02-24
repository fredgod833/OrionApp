package com.mddcore.usecases.response;

public record AuthResponse(
        Long id,
        String token,
        String picture
) {
}
