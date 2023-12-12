package com.openclassrooms.mddapi.repository;

import com.openclassrooms.mddapi.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

/**
 * Access post data
 */
@Repository
@EnableJpaRepositories
public interface PostRepository extends JpaRepository<Post, Integer> {
}
