package com.openclassrooms.mddapi.repository;

import com.openclassrooms.mddapi.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {
    @Modifying
    @Query(
        value = "SELECT * FROM mdd.posts WHERE topic_id in (:listTopicsIds) ORDER BY created_at DESC", nativeQuery = true
    )
    List<Post> findByTopicId(List<String> listTopicsIds);
}