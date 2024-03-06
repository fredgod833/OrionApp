package com.mdddetails.ut;

import com.mddcore.domain.models.RefreshToken;
import com.mdddetails.mapper.RefreshMapper;
import com.mdddetails.models.RefreshTokenEntity;
import com.mdddetails.repository.user.token.RefreshRepoImpl;
import com.mdddetails.repository.user.token.TokenJpaRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RefreshRepoImplUnitTest {

    @InjectMocks
    private RefreshRepoImpl refreshRepo;
    @Mock
    private TokenJpaRepo jpaRepo;
    @Mock
    private RefreshMapper refreshMapper;

    @Test
    public void findByToken_ShouldReturnRefreshToken_WhenTokenExists() {
        String token = "token123";
        RefreshTokenEntity refreshTokenEntity = new RefreshTokenEntity();
        RefreshToken refreshToken = new RefreshToken();

        when(jpaRepo.findByToken(token)).thenReturn(Optional.of(refreshTokenEntity));
        when(refreshMapper.toDomain(refreshTokenEntity)).thenReturn(refreshToken);

        Optional<RefreshToken> result = refreshRepo.findByToken(token);

        assertThat(result).isPresent().contains(refreshToken);
        verify(jpaRepo).findByToken(token);
        verify(refreshMapper).toDomain(refreshTokenEntity);
    }

    @Test
    public void deleteByUserId_ShouldInvokeDeletion_WhenCalled() {
        Long userId = 1L;

        refreshRepo.deleteByUserId(userId);

        verify(jpaRepo).deleteByUserId(userId);
    }

    @Test
    public void delete_ShouldDeleteRefreshToken_WhenRefreshTokenExists() {
        RefreshToken refreshToken = new RefreshToken();
        RefreshTokenEntity refreshTokenEntity = new RefreshTokenEntity();

        when(refreshMapper.toEntity(refreshToken)).thenReturn(refreshTokenEntity);

        refreshRepo.delete(refreshToken);

        verify(jpaRepo).delete(refreshTokenEntity);
        verify(refreshMapper).toEntity(refreshToken);
    }

    @Test
    public void save_ShouldSaveAndReturnToken_WhenRefreshTokenIsGiven() {
        RefreshToken refreshToken = new RefreshToken();
        RefreshTokenEntity refreshTokenEntity = new RefreshTokenEntity();
        refreshTokenEntity.setToken("token123");

        when(refreshMapper.toEntity(refreshToken)).thenReturn(refreshTokenEntity);
        when(jpaRepo.save(refreshTokenEntity)).thenReturn(refreshTokenEntity);

        String savedToken = refreshRepo.save(refreshToken);

        assertThat(savedToken).isEqualTo(refreshTokenEntity.getToken());
        verify(jpaRepo).save(refreshTokenEntity);
        verify(refreshMapper).toEntity(refreshToken);
    }
}
