package com.openclassrooms.mddapi.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.mddapi.models.Topic;
import com.openclassrooms.mddapi.repository.TopicRepository;

@RestController

@CrossOrigin(origins = "http://localhost:4200")
public class TopicController {

  @Autowired
  private TopicRepository topicRepository;

  @GetMapping("/api/topics")
  public List<Topic> getAllTopics() {
    return topicRepository.findAll();
  }
}
