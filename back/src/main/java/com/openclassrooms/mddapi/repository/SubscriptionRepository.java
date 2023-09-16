package com.openclassrooms.mddapi.repository;

import com.openclassrooms.mddapi.model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SubscriptionRepository extends JpaRepository<Subscription, Integer> {
    @Modifying
    @Query(
        value = "DELETE FROM mdd.subscriptions WHERE topic_id = :topicId and user_id = :userId", nativeQuery = true
    )
    void deleteSubscriptionByUserTopicIds(Integer topicId, Integer userId);

    @Modifying
    @Query(
        value = "SELECT topic_id FROM mdd.subscriptions WHERE user_id = :userId", nativeQuery = true
    )
    List<Integer> findTopicsByUserId(Integer userId);
}