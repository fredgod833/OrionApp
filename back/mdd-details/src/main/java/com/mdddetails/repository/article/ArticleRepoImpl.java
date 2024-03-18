package com.mdddetails.repository.article;

import com.mddcore.domain.models.Article;
import com.mddcore.domain.repository.IArticleRepository;
import com.mdddetails.mapper.ArticleMapper;
import com.mdddetails.models.ArticleEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Handles article data operations.
 */
@Repository
public class ArticleRepoImpl implements IArticleRepository {
    private final ArticleJpaRepo jpaRepo;
    private final ArticleMapper articleMapper;

    public ArticleRepoImpl(ArticleJpaRepo jpaRepo, ArticleMapper articleMapper) {
        this.jpaRepo = jpaRepo;
        this.articleMapper = articleMapper;
    }

    /**
     * Finds all articles.
     * @return list of articles
     */
    @Override
    @Transactional
    public List<Article> findAll() {
        return jpaRepo.findAll().stream().map(articleMapper::toDomain).collect(Collectors.toList());
    }

    /**
     * Finds an article by id.
     * @param id article ID
     * @return optional article
     */
    @Override
    @Transactional
    public Optional<Article> findById(Long id) {
        return jpaRepo.findById(id).map(articleMapper::toDomain);
    }

    /**
     * Saves an article.
     * @param entity article to save
     */
    @Override
    public void save(Article entity) {
        ArticleEntity article = articleMapper.toEntity(entity);
        jpaRepo.save(article);
    }
}
