package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.model.Subscription;
import com.openclassrooms.mddapi.model.Topic;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.SubscriptionRepository;
import com.openclassrooms.mddapi.repository.TopicRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

@Service
@Transactional
public class SubscriptionService {

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private TopicRepository topicRepository;

    public void saveSubscription(Integer topicId, User user) {
        Topic topic = topicRepository.findById(topicId).orElse(null);
        if (topic != null) {
            Subscription sub = new Subscription();
            sub.setUser(user);
            sub.setTopic(topic);
            sub.setCreatedAt(OffsetDateTime.now());
            subscriptionRepository.save(sub);
        }
    }

    public void deleteSubscription(Integer topicId, User user) {
        subscriptionRepository.deleteSubscriptionByUserTopicIds(topicId, user.getId());
    }

    public List<Integer> getAllIdsTopics(Integer userId) {
        return subscriptionRepository.findTopicsByUserId(userId);
    }
}