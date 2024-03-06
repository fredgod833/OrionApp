package com.mddcore.domain.repository;

import com.mddcore.domain.models.RefreshToken;

import java.util.Optional;

public interface IRefreshTokenRepository {
    Optional<RefreshToken> findByToken(String token);
    void deleteByUserId(Long id);
    void delete(RefreshToken refreshToken);
    String save(RefreshToken refreshToken);
}
