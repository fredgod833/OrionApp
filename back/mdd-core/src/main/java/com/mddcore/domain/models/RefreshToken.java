package com.mddcore.domain.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.Instant;

@AllArgsConstructor
@EqualsAndHashCode
@Data
@NoArgsConstructor
public class RefreshToken {
    private Long id;
    private User user;
    private String token;
    private Instant expirationDate;

//    public static RefreshToken newInstance(User user, String token, Instant expiryDate) {
//        return new RefreshToken(
//                null,
//                user,
//                token,
//                expiryDate
//        );
//    }
//
//    public RefreshToken(User user, String token, Instant expirationDate) {
//        this.id = null;
//        this.user = user;
//        this.token = token;
//        this.expirationDate = expirationDate;
//    }
}
