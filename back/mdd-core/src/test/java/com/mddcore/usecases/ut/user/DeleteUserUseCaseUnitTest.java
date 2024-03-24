package com.mddcore.usecases.ut.user;

import com.mddcore.domain.models.User;
import com.mddcore.domain.repository.IUserRepository;
import com.mddcore.usecases.user.DeleteUserUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DeleteUserUseCaseUnitTest {

    @InjectMocks
    private DeleteUserUseCase useCase;
    @Mock
    private IUserRepository repository;

    @Test
    public void deleteUser_ShouldReturnTrue_WithValidUserAndAuthUserId() {
        Long userId = 1L;
        User user = new User();
        user.setId(1L);

        doReturn(Optional.of(user)).when(repository).findById(userId);
        doNothing().when(repository).delete(user);

        DeleteUserUseCase.InputValues inputValues = new DeleteUserUseCase.InputValues(userId);

        DeleteUserUseCase.OutputValues outputValues = useCase.execute(inputValues);

        assertTrue(outputValues.isDeleted());
        verify(repository, times(1)).delete(user);
    }

    @Test
    public void deleteUser_ShouldReturnArgException_WithUserNull() {
        User user = new User();

        doThrow(new IllegalArgumentException("User not found, cant delete it")).when(repository).findById(1L);

        DeleteUserUseCase.InputValues inputValues = new DeleteUserUseCase.InputValues(1L);

        assertThatThrownBy(() -> useCase.execute(inputValues))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("User not found, cant delete it");
        verify(repository, times(0)).delete(user);
    }
}
