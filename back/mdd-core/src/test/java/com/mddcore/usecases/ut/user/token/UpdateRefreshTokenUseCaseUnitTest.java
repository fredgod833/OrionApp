package com.mddcore.usecases.ut.user.token;

import com.mddcore.domain.models.RefreshToken;
import com.mddcore.domain.models.User;
import com.mddcore.domain.repository.IRefreshTokenRepository;
import com.mddcore.usecases.user.token.UpdateRefreshTokenUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class UpdateRefreshTokenUseCaseUnitTest {

    @InjectMocks
    private UpdateRefreshTokenUseCase useCase;

    @Mock
    private IRefreshTokenRepository repository;

    @Test
    public void UpdateToken_ShouldReturnUpdatedRefreshToken_WithValidInput() {
        RefreshToken refreshToken = new RefreshToken(1L, new User(), "token", Instant.now().plusMillis(1));

        UpdateRefreshTokenUseCase.InputValues inputValues =
                new UpdateRefreshTokenUseCase.InputValues(refreshToken, 5000L);

        UpdateRefreshTokenUseCase.OutputValues outputValues = useCase.execute(inputValues);
        assertThat(outputValues.token()).isNotNull().isNotEqualTo(refreshToken.getToken());
        verify(repository, times(1)).save(refreshToken);
    }
}
