package com.mdddetails.repository.subscription;

import com.mddcore.domain.models.Subscription;
import com.mddcore.domain.repository.ISubscriptionRepository;
import com.mdddetails.mapper.SubscriptionMapper;
import com.mdddetails.models.SubscriptionEntity;
import org.springframework.stereotype.Repository;

@Repository
public class SubscriptionRepoImpl implements ISubscriptionRepository {

    private final SubscriptionJpaRepo repo;
    private final SubscriptionMapper mapper;

    public SubscriptionRepoImpl(SubscriptionJpaRepo repo, SubscriptionMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    @Override
    public void save(Subscription subscription) {
        SubscriptionEntity subscriptionEntity = mapper.toEntity(subscription);
        repo.save(subscriptionEntity);
    }

    @Override
    public void deleteById(Long id) {
        repo.deleteById(id);
    }
}
