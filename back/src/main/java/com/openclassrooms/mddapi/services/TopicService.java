package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.dto.TopicDto;
import com.openclassrooms.mddapi.models.Subscription;
import com.openclassrooms.mddapi.models.Topic;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.repository.SubscriptionRepository;
import com.openclassrooms.mddapi.repository.TopicRepository;
import com.openclassrooms.mddapi.repository.UserRepository;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

@Service
public class TopicService {

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private UserRepository userRepository;

    private TopicDto convertToDto(Topic topic) {
        Hibernate.initialize(topic.getSubscribers());
        TopicDto topicDto = new TopicDto();
        topicDto.setId(topic.getId());
        topicDto.setName(topic.getName());
        topicDto.setDescription(topic.getDescription());
        topicDto.setCreatedAt(topic.getCreatedAt());
        topicDto.setUpdatedAt(topic.getUpdatedAt());
        return topicDto;
    }

    private Topic convertToEntity(TopicDto topicDto) {
        Topic topic = new Topic();
        topic.setName(topicDto.getName());
        topic.setDescription(topicDto.getDescription());
        return topic;
    }

    @Transactional
    public List<TopicDto> findAllTopics() {
        return topicRepository.findAll().stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Transactional
    public TopicDto findTopicById(Long id) {
        Topic topic = topicRepository.findById(id).orElseThrow(() -> new RuntimeException("Topic not found"));
        return convertToDto(topic);
    }

    public TopicDto createTopic(TopicDto topicDto) {
        Topic topic = convertToEntity(topicDto);
        Topic savedTopic = topicRepository.save(topic);
        return convertToDto(savedTopic);
    }

    public TopicDto updateTopic(Long id, TopicDto topicDto) {
        Topic existingTopic = topicRepository.findById(id).orElseThrow(() -> new RuntimeException("Topic not found"));
        existingTopic.setName(topicDto.getName());
        existingTopic.setDescription(topicDto.getDescription());
        Topic updatedTopic = topicRepository.save(existingTopic);
        return convertToDto(updatedTopic);
    }

    public void deleteTopic(Long id) {
        Topic topic = topicRepository.findById(id).orElseThrow(() -> new RuntimeException("Topic not found"));
        topicRepository.delete(topic);
    }


    @Transactional
    public void subscribeToTopic(Long userId, Long topicId) {
      if(subscriptionRepository.findByUserIdAndTopicId(userId, topicId).isPresent()) {
          throw new RuntimeException("User already subscribed to this topic");
      }

      // Create a new subscription
      Subscription subscription = new Subscription();
      User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
      Topic topic = topicRepository.findById(topicId).orElseThrow(() -> new RuntimeException("Sujet non trouvé"));
      subscription.setUser(user);
      subscription.setTopic(topic);
      subscriptionRepository.save(subscription);
  }

  @Transactional
  public void unsubscribeFromTopic(Long userId, Long topicId) {
    Subscription existingSubscription = subscriptionRepository.findByUserIdAndTopicId(userId, topicId)
            .orElseThrow(() -> new RuntimeException("Subscription not found"));

    subscriptionRepository.delete(existingSubscription);
  }

  @Transactional
  public List<TopicDto> findSubscribedTopicsByUserId(Long userId) {
      List<Subscription> subscriptions = subscriptionRepository.findByUserId(userId);
      return subscriptions.stream()
              .map(subscription -> convertToDto(subscription.getTopic()))
              .collect(Collectors.toList());
  }
}
