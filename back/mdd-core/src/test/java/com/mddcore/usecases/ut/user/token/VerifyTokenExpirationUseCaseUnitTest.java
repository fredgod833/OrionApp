package com.mddcore.usecases.ut.user.token;

import com.mddcore.domain.models.RefreshToken;
import com.mddcore.domain.models.User;
import com.mddcore.domain.repository.IRefreshTokenRepository;
import com.mddcore.usecases.user.token.VerifyTokenExpirationUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class VerifyTokenExpirationUseCaseUnitTest {

    @InjectMocks
    private VerifyTokenExpirationUseCase useCase;
    @Mock
    private IRefreshTokenRepository repo;

    @Test
    public void VerifyTokenExpiration_ShouldReturnRefreshToken_WithValidTokenInputAndNotExpirate() {
        RefreshToken refreshToken = new RefreshToken(
                1L,
                new User(),
                "token",
                Instant.now().plusMillis(5000L)
        );
        VerifyTokenExpirationUseCase.InputValues inputValues = new VerifyTokenExpirationUseCase.InputValues(
                refreshToken
        );

        VerifyTokenExpirationUseCase.OutputValues outputValues = useCase.execute(inputValues);

        assertThat(outputValues.token().getToken()).isEqualTo(refreshToken.getToken());
        assertThat(outputValues.token().getExpirationDate()).isEqualTo(refreshToken.getExpirationDate());
        verify(repo, times(0)).delete(refreshToken);
    }

    @Test
    public void VerifyTokenExpiration_ShouldThrowError_WithValidTokenInputButExpire() {
        RefreshToken refreshToken = new RefreshToken(
                1L,
                new User(),
                "token",
                Instant.now()
        );
        VerifyTokenExpirationUseCase.InputValues inputValues = new VerifyTokenExpirationUseCase.InputValues(
                refreshToken
        );

        assertThatThrownBy(() -> useCase.execute(inputValues))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Refresh token was expired. Please make a new signIn request");
        verify(repo, times(1)).delete(refreshToken);
    }

}
