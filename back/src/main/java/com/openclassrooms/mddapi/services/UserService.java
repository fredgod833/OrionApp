package com.openclassrooms.mddapi.services;

import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.openclassrooms.mddapi.exception.BadRequestException;
import com.openclassrooms.mddapi.exception.NotFoundException;
import com.openclassrooms.mddapi.models.Topic;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.repository.TopicRepository;
import com.openclassrooms.mddapi.repository.UserRepository;

@Service
public class UserService {

  private final UserRepository userRepository;
  private final TopicRepository topicRepository;

  public UserService(UserRepository userRepository, TopicRepository topicRepository) {
    this.userRepository = userRepository;
    this.topicRepository = topicRepository;
  }

  public User create(User user) {
    return this.userRepository.save(user);
  }

  public void delete(Long id) {
    this.userRepository.deleteById(id);
  }

  public User findById(Long id) {
    return this.userRepository.findById(id).orElse(null);
  }

  public User findByEmail(String email) {
    return this.userRepository.findByEmail(email).orElse(null);
  }

  public void subscribeToTopic(User user, Long topicId) {
    Topic topic = this.topicRepository.findById(topicId).orElse(null);

    if (topic == null) {
      throw new NotFoundException();
    }

    boolean alreadyParticipate = user.getTopics().stream().anyMatch(o -> o.getId().equals(topicId));
    if(alreadyParticipate) {
      throw new BadRequestException();
    }

    user.getTopics().add(topic);

    this.userRepository.save(user);
  }

  public void unsubscribeToTopic(User user, Long topicId) {
    Topic topic = this.topicRepository.findById(topicId).orElse(null);

    if (topic == null) {
      throw new NotFoundException();
    }

    boolean alreadyParticipate = user.getTopics().stream().anyMatch(o -> o.getId().equals(topicId));
    if(!alreadyParticipate) {
      throw new BadRequestException();
    }

    user.setTopics(user.getTopics().stream().filter(o -> !o.getId().equals(topicId)).collect(Collectors.toList()));

    this.userRepository.save(user);
  }
}
