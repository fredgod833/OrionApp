package com.mddcore.usecases.ut.user;

import com.mddcore.domain.repository.IRefreshTokenRepository;
import com.mddcore.usecases.user.LogoutUserUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LogoutUserUseCaseUnitTest {
    @InjectMocks
    private LogoutUserUseCase useCase;
    @Mock
    private IRefreshTokenRepository repository;

    @Test
    public void LogoutUser_ShouldReturnTrue_WithValidInputUserId() {
        Long id = 1L;
        LogoutUserUseCase.InputValues inputValues = new LogoutUserUseCase.InputValues(id);
        doNothing().when(repository).deleteByUserId(id);

        LogoutUserUseCase.OutputValues outputValues = useCase.execute(inputValues);

        assertTrue(outputValues.isDeleted());
        verify(repository, times(1)).deleteByUserId(id);
    }

    @Test
    public void LogoutUser_ShouldThrowException_WithBadParams() {
        Long id = -1L;
        LogoutUserUseCase.InputValues inputValues = new LogoutUserUseCase.InputValues(id);

        doThrow(new IllegalArgumentException("Cant find refresh token for this user id")).when(repository).deleteByUserId(id);

        assertThatThrownBy(() -> useCase.execute(inputValues)).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("Cant find refresh token for this user, cant delete token");
        verify(repository, times(1)).deleteByUserId(id);
    }
}
