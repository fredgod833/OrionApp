package com.openclassrooms.mddapi.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.mddapi.models.Topic;
import com.openclassrooms.mddapi.repository.TopicRepository;

@RestController
@RequestMapping("/api/topics")
public class TopicController {

  @Autowired
  private TopicRepository topicRepository;

  @GetMapping
  public List<Topic> getAllTopics() {
    return topicRepository.findAll();
  }
}
