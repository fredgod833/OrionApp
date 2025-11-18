package com.openclassrooms.mddapi.repository;

import com.openclassrooms.mddapi.models.entities.UserTopicEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repo Spring Data JPA pour les themes utilisateur
 */
@Repository
public interface UserTopicRepository extends JpaRepository<UserTopicEntity, Integer> {

    Optional<UserTopicEntity> getUserTopicEntitiesById(Integer userId);

}
