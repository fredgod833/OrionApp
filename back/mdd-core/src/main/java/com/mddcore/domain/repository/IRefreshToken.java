package com.mddcore.domain.repository;

import com.mddcore.domain.models.RefreshToken;

import java.util.Optional;

public interface IRefreshToken {
    Optional<RefreshToken> findByToken(String token);
    void delete(RefreshToken refreshToken);
}
