package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.dto.ResponseDto;
import com.openclassrooms.mddapi.dto.TopicDto;
import com.openclassrooms.mddapi.model.Topic;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.service.TopicService;
import com.openclassrooms.mddapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/topics")
public class TopicController {
    @Autowired
    UserService userService;

    @Autowired
    TopicService topicService;

    private final ResponseDto responseDto = new ResponseDto();

    @PostMapping(
            value ="",
            consumes = { MediaType.MULTIPART_FORM_DATA_VALUE },
            produces = MediaType.APPLICATION_JSON_VALUE
        )
    public ResponseDto createTopic(@ModelAttribute TopicDto topicDto, Authentication authentication) {
        User user = userService.getUserByEmail(authentication.getName());
        topicService.saveTopic(topicDto, user);
        responseDto.setResponse("Topic created !");
        return responseDto;
    }

    @GetMapping(value = "", produces = { "application/json" })
    public ResponseEntity<List<Topic>> getAllTopics(){
        List<Topic> topics = this.topicService.getAllTopics();
        return ResponseEntity.ok().body(topics);
    }

    @GetMapping(value = "/subscription", produces = { "application/json" })
    public ResponseEntity<List<Topic>> getAllTopicsSubscription(@RequestParam List<String> values){
        List<Topic> topics = this.topicService.getAllTopicsSubscription(values);
        return ResponseEntity.ok().body(topics);
    }

    @GetMapping(value = "/none_subscription", produces = { "application/json" })
    public ResponseEntity<List<Topic>> getAllTopicsNoneSubscription(@RequestParam List<String> values){
        List<Topic> topics = this.topicService.getAllTopicsNoneSubscription(values);
        return ResponseEntity.ok().body(topics);
    }
}