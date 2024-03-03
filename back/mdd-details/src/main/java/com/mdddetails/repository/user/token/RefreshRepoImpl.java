package com.mdddetails.repository.user.token;

import com.mddcore.domain.models.RefreshToken;
import com.mddcore.domain.repository.IRefreshToken;
import com.mdddetails.mapper.RefreshMapper;
import com.mdddetails.models.RefreshTokenEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class RefreshRepoImpl implements IRefreshToken {

    private final TokenJpaRepo jpaRepo;

    private final RefreshMapper refreshMapper;

    public RefreshRepoImpl(TokenJpaRepo jpaRepo, RefreshMapper refreshMapper) {
        this.jpaRepo = jpaRepo;
        this.refreshMapper = refreshMapper;
    }

    @Override
    public Optional<RefreshToken> findByToken(String token) {
        return jpaRepo.findByToken(token).map(refreshMapper::toDomain);
    }

    @Override
    public void delete(RefreshToken refreshToken) {
        RefreshTokenEntity refreshTokenEntity = refreshMapper.toEntity(refreshToken);
        jpaRepo.delete(refreshTokenEntity);
    }
}
