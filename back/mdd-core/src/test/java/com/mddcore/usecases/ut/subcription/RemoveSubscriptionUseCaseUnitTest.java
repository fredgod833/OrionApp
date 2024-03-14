package com.mddcore.usecases.ut.subcription;

import com.mddcore.domain.repository.ISubscriptionRepository;
import com.mddcore.usecases.subscription.RemoveSubscriptionUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RemoveSubscriptionUseCaseUnitTest {
    @InjectMocks
    private RemoveSubscriptionUseCase useCase;
    @Mock
    private ISubscriptionRepository repository;

    @Test
    public void RemoveSubscription_ShouldReturnTrue_WithValidSubscriptionInputId() {
        Long subscriptionId = 1L;

        RemoveSubscriptionUseCase.InputValues inputValues = new RemoveSubscriptionUseCase.InputValues(subscriptionId);

        RemoveSubscriptionUseCase.OutputValues outputValues = useCase.execute(inputValues);

        assertTrue(outputValues.success());
        verify(repository, times(1)).deleteById(subscriptionId);
    }

    @Test
    public void RemoveSubscription_ShouldThrowException_WhenRepositoryCantDeleteWithInputId() {
        Long subscriptionId = 1L;

        RemoveSubscriptionUseCase.InputValues inputValues = new RemoveSubscriptionUseCase.InputValues(subscriptionId);

        doThrow(new IllegalArgumentException("Error while deleting subscription in db")).when(repository).deleteById(subscriptionId);

        assertThatThrownBy(() -> useCase.execute(inputValues)).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("Error while deleting subscription in db");
        verify(repository, times(1)).deleteById(subscriptionId);
    }
}
