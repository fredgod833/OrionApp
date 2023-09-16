package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.TopicDto;
import com.openclassrooms.mddapi.model.Topic;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.TopicRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class TopicService {
    @Autowired
    private TopicRepository topicRepository;

    public void saveTopic(TopicDto topicDto, User user) {
        Topic topic = new Topic();
        topic.setTitle(topicDto.getTitle());
        topic.setDescription(topicDto.getDescription());
        topic.setUser(user);
        topic.setCreatedAt(OffsetDateTime.now());
        topic.setUpdatedAt(OffsetDateTime.now());
        topicRepository.save(topic);
    }

    public List<Topic> getAllTopics(){
        List<Topic> listTopics = new ArrayList<Topic>();
        listTopics.addAll(topicRepository.findAll());
        return listTopics;
    }

    public List<Topic> getAllTopicsSubscription(List<String> listTopicIds){
        List<Topic> listTopics = new ArrayList<Topic>();
        listTopics.addAll(topicRepository.findTopicsSubscription(listTopicIds));
        return listTopics;
    }

    public List<Topic> getAllTopicsNoneSubscription(List<String> listTopicIds){
        List<Topic> listTopics = new ArrayList<Topic>();
        listTopics.addAll(topicRepository.findTopicsNoneSubscription(listTopicIds));
        return listTopics;
    }
}