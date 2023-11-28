package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.dto.TopicDto;
import com.openclassrooms.mddapi.mapper.TopicMapper;
import com.openclassrooms.mddapi.models.Topic;
import com.openclassrooms.mddapi.services.TopicService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/topics")
public class TopicController {


  private final TopicService topicService;
  private final TopicMapper topicMapper;

  public TopicController(TopicService topicService, TopicMapper topicMapper) {
      this.topicService = topicService;
      this.topicMapper = topicMapper;
  }

  // Retrouver tout les Themes de la DB
  @GetMapping("")
  public ResponseEntity<List<TopicDto>> findAll() {
      List<Topic> topic = this.topicService.findAll();

      return ResponseEntity.ok().body(this.topicMapper.toDto(topic));
  }

}
