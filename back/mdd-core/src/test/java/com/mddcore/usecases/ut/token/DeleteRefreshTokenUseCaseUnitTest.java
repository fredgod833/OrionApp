package com.mddcore.usecases.ut.token;

import com.mddcore.domain.repository.IRefreshTokenRepository;
import com.mddcore.usecases.user.token.DeleteRefreshTokenUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DeleteRefreshTokenUseCaseUnitTest {

    @InjectMocks
    private DeleteRefreshTokenUseCase useCase;

    @Mock
    private IRefreshTokenRepository repository;

    DeleteRefreshTokenUseCase.InputValues inputValues =
            new DeleteRefreshTokenUseCase.InputValues(1L);

    @Test
    public void DeleteRefreshToken_ShouldReturnEmptyValue_WithValidUserId() {

        assertThatCode(() -> {
            useCase.execute(inputValues);
        }).doesNotThrowAnyException();
        verify(repository, times(1)).deleteByUserId(1L);
    }

    @Test
    public void DeleteRefreshToken_ShouldHandleException_WhenDeletionFails() {
        doThrow(new RuntimeException("Database error")).when(repository).deleteByUserId(anyLong());

        assertThatThrownBy(() -> useCase.execute(inputValues))
                .isInstanceOf(IllegalArgumentException.class)
                .hasCauseInstanceOf(RuntimeException.class)
                .hasMessageContaining("Error while deleting token in db");

        verify(repository).deleteByUserId(1L);
    }
}
