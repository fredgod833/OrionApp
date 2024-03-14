package com.mddcore.usecases.ut.user;

import com.mddcore.domain.models.User;
import com.mddcore.domain.repository.IUserRepository;
import com.mddcore.usecases.auth.IPasswordEncodeFinal;
import com.mddcore.usecases.user.RegisterUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RegisterUseCaseUnitTest {
    @InjectMocks
    private RegisterUseCase useCase;
    @Mock
    private IUserRepository repository;
    @Mock
    private IPasswordEncodeFinal passwordEncoder;

    @Test
    public void RegisterUseCase_ShouldReturnSignInRequest_WithValidUserInput() {
        User user = new User();
        user.setEmail("test@unit.com");
        user.setUsername("theo");
        user.setId(null);
        user.setPassword("1567687354sdflsdhfQd&3");
        user.setSubscriptionList(null);
        RegisterUseCase.InputValues inputValues = new RegisterUseCase.InputValues(user);

        doReturn("1567687354sdflsdhfQd&3").when(passwordEncoder).encodePass(user.getPassword());
        doReturn(false).when(repository).existByEmail(user.getEmail());

        RegisterUseCase.OutputValues outputValues = useCase.execute(inputValues);

        assertThat(outputValues.signInRequest().email()).isEqualTo(user.getEmail());
        assertThat(outputValues.signInRequest().password()).isEqualTo("1567687354sdflsdhfQd&3");
        verify(repository, times(1)).save(user);
        verify(repository, times(1)).existByEmail(user.getEmail());
    }

    @Test
    public void RegisterUseCase_ShouldThrowError_WhenUserEmailExist() {
        User user = new User();
        RegisterUseCase.InputValues inputValues = new RegisterUseCase.InputValues(user);

        doReturn(true).when(repository).existByEmail(user.getEmail());

        assertThatThrownBy(() -> useCase.execute(inputValues))
                .isInstanceOf(IllegalArgumentException.class)
                        .hasMessageContaining("User email already exist");

        verify(repository, never()).save(user);
        verify(repository, times(1)).existByEmail(user.getEmail());
    }

    @Test
    public void RegisterUseCase_ShouldThrowError_WhenNotAValidPassword() {
        User user = new User();
        user.setPassword("");
        RegisterUseCase.InputValues inputValues = new RegisterUseCase.InputValues(user);

        doReturn("").when(passwordEncoder).encodePass(user.getPassword());

        assertThatThrownBy(() -> useCase.execute(inputValues))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Password security check failed");

        verify(repository, never()).save(user);
        verify(repository, times(1)).existByEmail(user.getEmail());
    }
}
