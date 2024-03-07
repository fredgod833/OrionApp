package com.openclassrooms.mddapi.responses;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthResponse {
    String token;

    public AuthResponse(String token) {
        this.token = token;
    }
}
