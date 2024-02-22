package com.mddcore.usecases.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private Long id;
    private String email;
    private String username;

//
//    public JwtResponse(String token, Long id, String email, String username) {
//        this.token = token;
//        this.id = id;
//        this.email = email;
//        this.username = username;
//    }
//
//    public void setToken(String token) {
//        this.token = token;
//    }
//
//    public void setType(String type) {
//        this.type = type;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public void setUsername(String username) {
//        this.username = username;
//    }
//
//    @Override
//    public String toString() {
//        return "JwtResponse{" +
//                "token='" + token + '\'' +
//                ", type='" + type + '\'' +
//                ", id=" + id +
//                ", email='" + email + '\'' +
//                ", username='" + username + '\'' +
//                '}';
//    }
}
