package com.openclassrooms.mddapi.repository;

import com.openclassrooms.mddapi.model.Comments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@EnableJpaRepositories
@Repository
public interface CommentsRepository extends JpaRepository<Comments, Integer> {
}
