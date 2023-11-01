package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.dto.TopicDto;
import com.openclassrooms.mddapi.services.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/topics")
@CrossOrigin(origins = "http://localhost:4200")
public class TopicController {

    @Autowired
    private TopicService topicService;

    @GetMapping
    public ResponseEntity<List<TopicDto>> getAllTopics() {
        List<TopicDto> topics = topicService.findAllTopics();
        return new ResponseEntity<>(topics, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TopicDto> getTopic(@PathVariable Long id) {
        TopicDto topic = topicService.findTopicById(id);
        return new ResponseEntity<>(topic, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<TopicDto> createTopic(@RequestBody TopicDto topicDto) {
        TopicDto createdTopic = topicService.createTopic(topicDto);
        return new ResponseEntity<>(createdTopic, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TopicDto> updateTopic(@PathVariable Long id, @RequestBody TopicDto topicDto) {
        TopicDto updatedTopic = topicService.updateTopic(id, topicDto);
        return new ResponseEntity<>(updatedTopic, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTopic(@PathVariable Long id) {
        topicService.deleteTopic(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/subscribed/{userId}")
    public ResponseEntity<List<TopicDto>> getSubscribedTopicsByUserId(@PathVariable Long userId) {
        List<TopicDto> topics = topicService.findSubscribedTopicsByUserId(userId);
        return new ResponseEntity<>(topics, HttpStatus.OK);
    }

    @PostMapping("/{topicId}/subscribe/{userId}")
    public ResponseEntity<Void> subscribeToTopic(@PathVariable Long topicId, @PathVariable Long userId) {
        topicService.subscribeToTopic(userId, topicId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/{topicId}/unsubscribe/{userId}")
    public ResponseEntity<Void> unsubscribeUserFromTopic(@PathVariable Long topicId, @PathVariable Long userId) {
        topicService.unsubscribeFromTopic(userId, topicId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }



}
