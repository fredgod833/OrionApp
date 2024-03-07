package com.mdddetails.repository.user.token;

import com.mddcore.domain.models.RefreshToken;
import com.mddcore.domain.repository.IRefreshTokenRepository;
import com.mdddetails.mapper.RefreshMapper;
import com.mdddetails.models.RefreshTokenEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class RefreshRepoImpl implements IRefreshTokenRepository {

    private final TokenJpaRepo repository;
    private final RefreshMapper refreshMapper;

    Logger logger = LoggerFactory.getLogger(RefreshRepoImpl.class);

    public RefreshRepoImpl(TokenJpaRepo repository, RefreshMapper refreshMapper) {
        this.repository = repository;
        this.refreshMapper = refreshMapper;
    }

    @Override
    public Optional<RefreshToken> findByToken(String token) {
        return repository.findByToken(token).map(refreshMapper::toDomain);
    }

    @Override
    public void deleteByUserId(Long id) {
        logger.info("Before deleteByUserId in refreshRepoImpl, user id {}", id);
        repository.deleteByUserId(id);

        logger.info("after deleteByUserId in refreshRepoImpl");
    }

    @Override
    public void delete(RefreshToken refreshToken) {
        RefreshTokenEntity refreshTokenEntity = refreshMapper.toEntity(refreshToken);
        repository.delete(refreshTokenEntity);
    }

    @Override
    public String save(RefreshToken refreshToken) {
        RefreshTokenEntity refreshTokenEntity = refreshMapper.toEntity(refreshToken);
        repository.save(refreshTokenEntity);
        return refreshTokenEntity.getToken();
    }
}
