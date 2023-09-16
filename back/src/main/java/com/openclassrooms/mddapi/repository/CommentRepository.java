package com.openclassrooms.mddapi.repository;

import com.openclassrooms.mddapi.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

    @Modifying
    @Query(
        value = "SELECT * FROM mdd.comments WHERE post_id = :postId ORDER BY created_at DESC", nativeQuery = true
    )
    List<Comment> findByPostIds(Integer postId);
}