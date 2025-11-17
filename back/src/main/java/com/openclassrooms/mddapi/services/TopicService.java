package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.exceptions.InvalidTopicIdException;
import com.openclassrooms.mddapi.exceptions.InvalidUserException;
import com.openclassrooms.mddapi.mapper.TopicMapper;
import com.openclassrooms.mddapi.models.dto.TopicDto;
import com.openclassrooms.mddapi.models.entities.TopicEntity;
import com.openclassrooms.mddapi.models.entities.UserTopicEntity;
import com.openclassrooms.mddapi.repository.TopicRepository;
import com.openclassrooms.mddapi.repository.UserTopicRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TopicService {

    private final TopicRepository topicRepository;
    private final TopicMapper topicMapper;

    private final UserTopicRepository userTopicRepository;


    public TopicService(TopicRepository topicRepository, UserTopicRepository userTopicRepository, TopicMapper topicMapper) {

        this.topicRepository = topicRepository;
        this.topicMapper = topicMapper;
        this.userTopicRepository = userTopicRepository;

    }

    public Collection<TopicDto> findAll() {

        return topicMapper.toDto(this.topicRepository.findAll());

    }

    public Optional<TopicDto> findById(Integer id) {

        TopicEntity topic = topicRepository.findById(id).orElse(null);
        return Optional.of(topicMapper.toDto(topic));

    }

    public TopicDto createTopic(final String name, final String description) {

        TopicEntity result = this.topicRepository.save(new TopicEntity(name,description));
        return topicMapper.toDto(result);

    }

    public Collection<TopicDto> getSubscribedTopics(Integer userId) throws InvalidUserException {

       Optional<UserTopicEntity> userTopics = this.userTopicRepository.getUserTopicEntitiesById(userId);
       if (userTopics.isEmpty()) {
           return Collections.emptyList();
       }

       Collection<TopicDto> results = topicMapper.toDto(userTopics.get().getTopics());
       results.forEach(t -> t.setSubscribed(true));
       return results;

    }

    public Collection<TopicDto> getAvailableTopics(Integer userId) throws InvalidUserException {

        Collection<TopicDto> all = topicMapper.toDto(this.topicRepository.findAll());
        Optional<UserTopicEntity> userTopics = this.userTopicRepository.getUserTopicEntitiesById(userId);
        if (userTopics.isEmpty()) {
            return all;
        }
        Collection<TopicDto> subscribed = topicMapper.toDto(userTopics.get().getTopics());
        all.stream().filter(subscribed::contains).forEach(topicDto -> topicDto.setSubscribed(true));
        return all;
    }

    public void subscribeTopic(Integer userId, Integer topicId) throws InvalidUserException, InvalidTopicIdException {

        UserTopicEntity userTopics = this.userTopicRepository.getUserTopicEntitiesById(userId).orElseThrow(() -> new InvalidUserException("Utilisateur non reconnu"));
        boolean alreadySubscribed = userTopics.getTopics().stream().anyMatch(o -> o.getId().equals(topicId));
        if(alreadySubscribed) {
            return;
        }
        TopicEntity topicEntity = this.topicRepository.findById(topicId).orElseThrow(() -> new InvalidTopicIdException("Theme non trouvé"));
        userTopics.getTopics().add(topicEntity);
        this.userTopicRepository.save(userTopics);

    }

    public void unSubscribeTopic(Integer userId, Integer topicId) throws InvalidUserException {

        UserTopicEntity userTopics = this.userTopicRepository.getUserTopicEntitiesById(userId).orElseThrow(() -> new InvalidUserException("Utilisateur non reconnu"));
        boolean alreadySubscribed = userTopics.getTopics().stream().anyMatch(o -> o.getId().equals(topicId));
        if(!alreadySubscribed) {
            return;
        }
        userTopics.setTopics(userTopics.getTopics().stream().filter(topic -> !topic.getId().equals(topicId)).collect(Collectors.toList()));
        this.userTopicRepository.save(userTopics);

    }

}
