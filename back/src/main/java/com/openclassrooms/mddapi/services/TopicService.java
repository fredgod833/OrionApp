package com.openclassrooms.mddapi.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.openclassrooms.mddapi.models.Topic;
import com.openclassrooms.mddapi.repository.TopicRepository;

@Service
public class TopicService {

  private final TopicRepository topicRepository;

  public TopicService(TopicRepository topicRepository) {
    this.topicRepository = topicRepository;
  }

  public void delete(Long id) {
    this.topicRepository.deleteById(id);
  }

  public Topic findById(Long id) {
    return this.topicRepository.findById(id).orElse(null);
  }

  public List<Topic> findAll() {
    return this.topicRepository.findAll();
  }

  public Topic create(Topic topic) {
    return this.topicRepository.save(topic);
  }
}
