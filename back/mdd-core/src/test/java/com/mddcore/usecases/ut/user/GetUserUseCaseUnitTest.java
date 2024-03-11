package com.mddcore.usecases.ut.user;

import com.mddcore.domain.models.User;
import com.mddcore.domain.repository.IUserRepository;
import com.mddcore.usecases.user.GetUserUseCase;
import com.mddcore.usecases.user.token.FindByTokenUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GetUserUseCaseUnitTest {

    @InjectMocks
    private GetUserUseCase useCase;
    @Mock
    private IUserRepository repository;

    @Test
    public void FindById_ShouldReturnUser_WithValidId() {
        User user = new User();
        user.setEmail("test@unit.com");
        GetUserUseCase.InputValues inputValues = new GetUserUseCase.InputValues(1L);

        doReturn(Optional.of(user)).when(repository).findById(1L);

        GetUserUseCase.OutputValues outputValues = useCase.execute(inputValues);

        assertThat(outputValues.user().getEmail()).isEqualTo(user.getEmail());
        verify(repository, times(1)).findById(1L);
    }

    @Test
    public void FindById_ShouldException_WithUserNull() {
        GetUserUseCase.InputValues inputValues = new GetUserUseCase.InputValues(1L);

        assertThatThrownBy(() -> useCase.execute(inputValues))
                .isInstanceOf(IllegalArgumentException.class)
                        .hasMessageContaining("User not found with id : " + inputValues.id());
        verify(repository, times(1)).findById(1L);
    }

}
