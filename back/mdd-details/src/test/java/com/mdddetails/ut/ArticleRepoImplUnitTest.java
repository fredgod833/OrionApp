package com.mdddetails.ut;

import com.mddcore.domain.models.Article;
import com.mdddetails.mapper.ArticleMapper;
import com.mdddetails.models.ArticleEntity;
import com.mdddetails.repository.article.ArticleJpaRepo;
import com.mdddetails.repository.article.ArticleRepoImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ArticleRepoImplUnitTest {
    @InjectMocks
    private ArticleRepoImpl articleRepo;
    @Mock
    private ArticleJpaRepo jpaRepo;
    @Mock
    private ArticleMapper articleMapper;

    @Test
    public void findAll_ShouldReturnListOfArticles_WhenArticlesExist() {
        ArticleEntity articleEntity1 = new ArticleEntity();
        articleEntity1.setContent("Test unit content 1");
        ArticleEntity articleEntity2 = new ArticleEntity();
        articleEntity2.setContent("Test unit content 2");
        Article article1 = new Article();
        article1.setContent("Test unit content 1");
        Article article2 = new Article();
        article2.setContent("Test unit content 2");

        when(jpaRepo.findAll()).thenReturn(List.of(articleEntity1, articleEntity2));
        when(articleMapper.toDomain(articleEntity1)).thenReturn(article1);
        when(articleMapper.toDomain(articleEntity2)).thenReturn(article2);

        List<Article> articles = articleRepo.findAll();

        assertThat(articles.get(0).getContent()).isEqualTo(articleEntity1.getContent());
        assertThat(articles.get(1).getContent()).isEqualTo(articleEntity2.getContent());
        verify(jpaRepo).findAll();
        verify(articleMapper, times(2)).toDomain(any(ArticleEntity.class));
    }

    @Test
    public void findById_ShouldReturnArticle_WhenArticleExists() {
        Long id = 1L;

        ArticleEntity articleEntity = new ArticleEntity();
        articleEntity.setContent("test content");

        Article article = new Article();
        article.setContent("test content");

        when(jpaRepo.findById(id)).thenReturn(Optional.of(articleEntity));
        when(articleMapper.toDomain(articleEntity)).thenReturn(article);

        Optional<Article> result = articleRepo.findById(id);

        assertThat(result.get().getContent()).isEqualTo(articleEntity.getContent());
        verify(jpaRepo).findById(id);
        verify(articleMapper).toDomain(articleEntity);
    }

    @Test
    public void save_ShouldPersistArticle_WhenArticleIsValid() {
        Article article = new Article();
        ArticleEntity articleEntity = new ArticleEntity();

        when(articleMapper.toEntity(article)).thenReturn(articleEntity);
        when(jpaRepo.save(articleEntity)).thenReturn(articleEntity);

        articleRepo.save(article);

        verify(jpaRepo).save(articleEntity);
        verify(articleMapper).toEntity(article);
    }
}
