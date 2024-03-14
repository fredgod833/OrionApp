package com.mdddetails.ut;

import com.mddcore.domain.models.Subscription;
import com.mdddetails.mapper.SubscriptionMapper;
import com.mdddetails.models.SubscriptionEntity;
import com.mdddetails.repository.subscription.SubscriptionJpaRepo;
import com.mdddetails.repository.subscription.SubscriptionRepoImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SubscriptionRepoImplUnitTest {
    @InjectMocks
    private SubscriptionRepoImpl useCase;
    @Mock
    private SubscriptionJpaRepo jpaRepo;
    @Mock
    private SubscriptionMapper mapper;

    @Test
    public void save_ShouldCallMapSubscriptionToSubscriptionEntityAndCallJpaRepo() {
        SubscriptionEntity subscriptionEntity = mock(SubscriptionEntity.class);
        Subscription subscription = mock(Subscription.class);

        doReturn(subscriptionEntity).when(mapper).toEntity(subscription);

        useCase.save(subscription);

        verify(jpaRepo, times(1)).save(subscriptionEntity);
    }

    @Test
    public void DeleteById_ShouldCallDeleteByIdFromJpaRepo() {
        useCase.deleteById(1L);

        verify(jpaRepo, times(1)).deleteById(1L);
    }
}
