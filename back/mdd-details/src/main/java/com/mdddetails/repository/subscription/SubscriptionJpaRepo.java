package com.mdddetails.repository.subscription;

import com.mdddetails.models.SubscriptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionJpaRepo extends JpaRepository<SubscriptionEntity, Long> {
}
