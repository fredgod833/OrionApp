package com.mdddetails.repository.comment;

import com.mdddetails.models.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentJpaRepo extends JpaRepository<CommentEntity, Long> {
}
