package com.openclassrooms.p6.service;

import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.p6.model.Comments;
import com.openclassrooms.p6.model.Subscriptions;
import com.openclassrooms.p6.repository.SubscriptionRepository;

import lombok.Data;

@Data
@Service
public class SubscriptionsService {

    /**
     * 
     * Subscriptions repo to perform database operations on the {@link Comments}
     * entity.
     */
    @Autowired
    private SubscriptionRepository subscriptionRepository;

    public Optional<Subscriptions> getSubscriptionById(Long id) {
        return subscriptionRepository.findById(id);
    }

    public void toggleThemeSubscription(Subscriptions subscription, boolean isSubscribed) {
        subscription.setIsSubscribed(isSubscribed);

        subscriptionRepository.save(subscription);
    }

    public Iterable<Subscriptions> findAllUserSubscriptions(Long userId) {
        return subscriptionRepository.findAllByUserId(userId);
    }
}
