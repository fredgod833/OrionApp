package com.mddcommon.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private Long id;
    private String email;
    private String username;

    public JwtResponse(String token, Long id, String email, String username) {
        this.token = token;
        this.id = id;
        this.email = email;
        this.username = username;
    }
}
