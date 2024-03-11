package com.mddcore.usecases.ut.user.token;


import com.mddcore.domain.models.RefreshToken;
import com.mddcore.domain.models.User;
import com.mddcore.domain.repository.IRefreshTokenRepository;
import com.mddcore.domain.repository.IUserRepository;
import com.mddcore.usecases.user.token.CreateRefreshTokenUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CreateRefreshTokenUseCaseUnitTest {

    @InjectMocks
    private CreateRefreshTokenUseCase useCase;
    @Mock
    private IRefreshTokenRepository refreshTokenRepository;
    @Mock
    private IUserRepository userRepository;

    @Test
    public void CreateRefreshToken_ShouldReturnToken_WithValidInput() {
        User user = new User();

        CreateRefreshTokenUseCase.InputValues inputValues =
                new CreateRefreshTokenUseCase.InputValues(1L, 5000L);

        doReturn(Optional.of(user)).when(userRepository).findById(1L);

        CreateRefreshTokenUseCase.OutputValues outputValues = useCase.execute(inputValues);

        assertThat(outputValues.token()).isNotNull();

        verify(refreshTokenRepository).save(any(RefreshToken.class));
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    public void CreateRefreshToken_ShouldReturnException_WithNullUser() {
        CreateRefreshTokenUseCase.InputValues inputValues =
                new CreateRefreshTokenUseCase.InputValues(null, 5000L);

        assertThatThrownBy(() -> useCase.execute(inputValues))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("User do not exist");

    }
}
