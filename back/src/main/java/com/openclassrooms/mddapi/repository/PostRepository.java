package com.openclassrooms.mddapi.repository;

import com.openclassrooms.mddapi.models.entities.PostEntity;
import com.openclassrooms.mddapi.models.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * Repo Spring Data JPA pour les articles
 */
@Repository
public interface PostRepository extends JpaRepository<PostEntity, Integer> {

    Collection<PostEntity> findPostEntitiesByTopicId(Integer topicId);

    Collection<PostEntity> findPostEntitiesByAuthor(UserEntity author);

}
