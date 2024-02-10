package com.openclassrooms.p6.payload.request;

import jakarta.validation.constraints.NotBlank;

/**
 * Request payload for user login.
 */
public record LoginRequest(
        @NotBlank(message = "Email cannot be blank or null") String email,
        @NotBlank(message = "Password cannot be blank or null") String password) {
}