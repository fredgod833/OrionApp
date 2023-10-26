package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.dto.TopicDto;
import com.openclassrooms.mddapi.models.Topic;
import com.openclassrooms.mddapi.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TopicService {

    @Autowired
    private TopicRepository topicRepository;

    private TopicDto convertToDto(Topic topic) {
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

    public List<TopicDto> findAllTopics() {
        return topicRepository.findAll().stream().map(this::convertToDto).collect(Collectors.toList());
    }

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
}
