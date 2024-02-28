package com.mdddetails.repository.article;

import com.mdddetails.models.ArticleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleJpaRepo extends JpaRepository<ArticleEntity, Long> {
}
