package com.mddcore.domain.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@EqualsAndHashCode
@Data
@NoArgsConstructor
public class RefreshToken {
    private Long id;
    private User user;
    private String accessToken;
    private String refreshToken;

    public static RefreshToken newInstance(User user, String accessToken, String refreshToken) {
        return new RefreshToken(
                null,
                user,
                accessToken,
                refreshToken
        );
    }
}
