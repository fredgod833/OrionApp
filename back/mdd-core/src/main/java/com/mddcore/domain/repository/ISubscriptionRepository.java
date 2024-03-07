package com.mddcore.domain.repository;

import com.mddcore.domain.models.Subscription;

public interface ISubscriptionRepository {
    void save(Subscription subscription);
    void deleteById(Long id);
}
