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
}
