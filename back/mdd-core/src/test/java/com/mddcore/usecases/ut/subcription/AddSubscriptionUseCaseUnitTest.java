package com.mddcore.usecases.ut.subcription;

import com.mddcore.domain.models.Subject;
import com.mddcore.domain.models.Subscription;
import com.mddcore.domain.models.User;
import com.mddcore.domain.repository.ISubjectRepository;
import com.mddcore.domain.repository.ISubscriptionRepository;
import com.mddcore.domain.repository.IUserRepository;
import com.mddcore.usecases.subscription.AddSubscriptionUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AddSubscriptionUseCaseUnitTest {
    @InjectMocks
    private AddSubscriptionUseCase useCase;
    @Mock
    private ISubscriptionRepository subscriptionRepository;
    @Mock
    private IUserRepository userRepository;
    @Mock
    private ISubjectRepository subjectRepository;

    @Test
    public void AddSubscriptionUseCase_ShouldReturnTrue_WithValidUserAndSubjectId() {
        Long subjectId = 1L;
        Long userId = 1L;
        User user = new User();
        Subject subject = new Subject();

        AddSubscriptionUseCase.InputValues inputValues = new AddSubscriptionUseCase.InputValues(userId, subjectId);

        doReturn(Optional.of(user)).when(userRepository).findById(userId);
        doReturn(Optional.of(subject)).when(subjectRepository).findById(subjectId);

        AddSubscriptionUseCase.OutputValues outputValues = useCase.execute(inputValues);

        assertTrue(outputValues.isAdded());
        verify(userRepository, times(1)).findById(userId);
        verify(subjectRepository, times(1)).findById(subjectId);
        verify(subscriptionRepository, times(1)).save(any());

    }

    @Test
    public void AddSubscriptionUseCase_ShouldThrowException_WithUserNull() {
        Long subjectId = 1L;
        Long userId = 1L;

        AddSubscriptionUseCase.InputValues inputValues = new AddSubscriptionUseCase.InputValues(userId, subjectId);

        doThrow(new IllegalArgumentException("User cannot be null")).when(userRepository).findById(userId);

        assertThatThrownBy(() -> useCase.execute(inputValues))
                .isInstanceOf(IllegalArgumentException.class)
                        .hasMessageContaining("User cannot be null");

        verify(userRepository, times(1)).findById(userId);
        verify(subjectRepository, never()).findById(subjectId);
        verify(subscriptionRepository, never()).save(any());

    }

    @Test
    public void AddSubscriptionUseCase_ShouldThrowException_WithSubjectNull() {
        Long subjectId = 1L;
        Long userId = 1L;
        User user = new User();

        AddSubscriptionUseCase.InputValues inputValues = new AddSubscriptionUseCase.InputValues(userId, subjectId);

        doReturn(Optional.of(user)).when(userRepository).findById(userId);
        doThrow(new IllegalArgumentException("Subject cannot be null")).when(subjectRepository).findById(subjectId);

        assertThatThrownBy(() -> useCase.execute(inputValues))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Subject cannot be null");

        verify(userRepository, times(1)).findById(userId);
        verify(subjectRepository, times(1)).findById(subjectId);
        verify(subscriptionRepository, never()).save(any());
    }

    @Test
    public void AddSubscriptionUseCase_ShouldThrowException_WhenUserAlreadySubscribed() {
        Long subjectId = 1L;
        Long userId = 1L;
        User user = new User();

        Subject subject = new Subject();
        subject.setId(1L);

        Subscription subscription = new Subscription();
        subscription.setSubject(subject);
        List<Subscription> subscriptionList = new ArrayList<>();
        subscriptionList.add(subscription);

        user.setSubscriptionList(subscriptionList);

        AddSubscriptionUseCase.InputValues inputValues = new AddSubscriptionUseCase.InputValues(userId, subjectId);

        doReturn(Optional.of(user)).when(userRepository).findById(userId);
        doReturn(Optional.of(subject)).when(subjectRepository).findById(subjectId);

        assertThatThrownBy(() -> useCase.execute(inputValues))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Already subscribed to this subject");

        verify(userRepository, times(1)).findById(userId);
        verify(subjectRepository, times(1)).findById(subjectId);
        verify(subscriptionRepository, never()).save(any());
    }
}
