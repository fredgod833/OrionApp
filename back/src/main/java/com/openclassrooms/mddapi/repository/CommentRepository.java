package com.openclassrooms.mddapi.repository;

import com.openclassrooms.mddapi.models.entities.CommentEntity;
import com.openclassrooms.mddapi.models.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Integer> {

    Collection<CommentEntity> findCommentEntitiesByPostId(Integer postId);

    Collection<CommentEntity> findCommentEntitiesByPostIdAndUser(Integer postId, UserEntity user);

}
