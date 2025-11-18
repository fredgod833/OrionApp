package com.openclassrooms.mddapi.repository;

import com.openclassrooms.mddapi.models.entities.TopicEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repo Spring Data JPA pour les themes
 */
@Repository
public interface TopicRepository extends JpaRepository<TopicEntity, Integer> {

}
