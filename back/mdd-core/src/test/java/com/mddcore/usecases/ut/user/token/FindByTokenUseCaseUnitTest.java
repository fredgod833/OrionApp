package com.mddcore.usecases.ut.user.token;

import com.mddcore.domain.models.RefreshToken;
import com.mddcore.domain.models.User;
import com.mddcore.domain.repository.IRefreshTokenRepository;
import com.mddcore.usecases.user.token.FindByTokenUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FindByTokenUseCaseUnitTest {

    @InjectMocks
    private FindByTokenUseCase useCase;

    @Mock
    private IRefreshTokenRepository repository;

    @Test
    public void FindByToken_ShouldReturnRefreshTokenModel_WithValidToken() {
        RefreshToken refreshToken = new RefreshToken(1L, new User(), "token", Instant.now());
        FindByTokenUseCase.InputValues inputValues = new FindByTokenUseCase.InputValues(
                "token"
        );

        doReturn(Optional.of(refreshToken)).when(repository).findByToken("token");

        FindByTokenUseCase.OutputValues outputValues = useCase.execute(inputValues);

        assertThat(outputValues.refreshToken().getToken()).isEqualTo(refreshToken.getToken());
        verify(repository, times(1)).findByToken("token");
    }
}
