package com.mdddetails.repository.article;

import com.mddcore.domain.models.Article;
import com.mddcore.domain.repository.IArticleRepository;
import com.mdddetails.mapper.ArticleMapper;
import com.mdddetails.models.ArticleEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class ArticleRepoImpl implements IArticleRepository {
    private final ArticleJpaRepo jpaRepo;
    private final ArticleMapper articleMapper;

    public ArticleRepoImpl(ArticleJpaRepo jpaRepo, ArticleMapper articleMapper) {
        this.jpaRepo = jpaRepo;
        this.articleMapper = articleMapper;
    }

    @Override
    public List<Article> findAll() {
        return jpaRepo.findAll().stream().map(articleMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Article> findById(Long id) {
        return jpaRepo.findById(id).map(articleMapper::toDomain);
    }

    @Override
    public void save(Article entity) {
        ArticleEntity article = articleMapper.toEntity(entity);
        jpaRepo.save(article);
    }
}
