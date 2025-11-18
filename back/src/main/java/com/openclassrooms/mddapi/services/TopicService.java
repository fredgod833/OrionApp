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

/**
 * Service de gestion des Themes
 */
@Service
public class TopicService {

    private final TopicRepository topicRepository;
    private final TopicMapper topicMapper;
    private final UserTopicRepository userTopicRepository;

    /**
     * Constructeur
     *
     * @param topicRepository : repository des themes
     * @param userTopicRepository : repository des themes utilisateur
     * @param topicMapper : mapper entity <-> DTO mapstruct des themes
     */
    public TopicService(TopicRepository topicRepository, UserTopicRepository userTopicRepository, TopicMapper topicMapper) {

        this.topicRepository = topicRepository;
        this.topicMapper = topicMapper;
        this.userTopicRepository = userTopicRepository;

    }

    /**
     * Liste la totalité des thèmes existants
     * @return la liste des DTO themes.
     */
    public Collection<TopicDto> findAll() {

        return topicMapper.toDto(this.topicRepository.findAll());

    }

    /**
     * Recherche un thème avec son identifiant.
     * @param id : identifiant du thème
     * @return le DTO du theme si il a été trouvé
     */
    public Optional<TopicDto> findById(Integer id) {

        TopicEntity topic = topicRepository.findById(id).orElse(null);
        return Optional.of(topicMapper.toDto(topic));

    }

    /**
     * Créé un nouveau thème
     * @param name : le nom du thème
     * @param description : la description du thème
     * @return le DTO du theme
     */
    public TopicDto createTopic(final String name, final String description) {

        TopicEntity result = this.topicRepository.save(new TopicEntity(name,description));
        return topicMapper.toDto(result);

    }

    /**
     * Recherche les thèmes souscrits par l'utilisateur
     * @param userId : l'identifiant de l'utilisateur
     * @return la liste des DTO themes
     */
    public Collection<TopicDto> getSubscribedTopics(Integer userId) {

       Optional<UserTopicEntity> userTopics = this.userTopicRepository.getUserTopicEntitiesById(userId);
       if (userTopics.isEmpty()) {
           return Collections.emptyList();
       }

       Collection<TopicDto> results = topicMapper.toDto(userTopics.get().getTopics());
       results.forEach(t -> t.setSubscribed(true));
       return results;

    }

    /**
     * Recherche la liste des themes disponibles pour un utilisateur (déjà souscrits ou non)
     * @param userId : l'identifiant de l'utilisateur
     * @return la liste des DTO themes
     */
    public Collection<TopicDto> getAvailableTopics(Integer userId) {

        Collection<TopicDto> all = topicMapper.toDto(this.topicRepository.findAll());
        Optional<UserTopicEntity> userTopics = this.userTopicRepository.getUserTopicEntitiesById(userId);
        if (userTopics.isEmpty()) {
            return all;
        }
        Collection<TopicDto> subscribed = topicMapper.toDto(userTopics.get().getTopics());
        all.stream().filter(subscribed::contains).forEach(topicDto -> topicDto.setSubscribed(true));
        return all;
    }

    /**
     * Enregistre la souscription de l'utilisateur pour un theme.
     * @param userId : l'identifiant de l'utilisateur
     * @param topicId : identifiant du thème
     * @throws InvalidUserException si l'utilisateur n'existe pas
     * @throws InvalidTopicIdException si le theme est inconnu
     */
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

    /**
     * Enregistre le désabonnement de l'utilisateur pour un theme
     * @param userId : l'identifiant de l'utilisateur
     * @param topicId : identifiant du thème
     * @throws InvalidUserException si l'utilisateur n'existe pas
     */
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
