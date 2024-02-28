package com.mddcore.domain.repository;

import com.mddcore.domain.models.Article;

import java.util.List;
import java.util.Optional;

public interface IArticleRepository {
    List<Article> findAll();
    Optional<Article> findById(Long id);
    void save(Article entity);
}
