package com.openclassrooms.p6.payload.response;

/**
 * Response payload for authentication operations, containing a JWT.
 */
public record AuthResponse(String token) {
}