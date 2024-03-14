package com.mddcore.usecases.ut.article;

import com.mddcore.domain.models.Article;
import com.mddcore.domain.repository.IArticleRepository;
import com.mddcore.usecases.article.GetArticleUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GetArticleUseCaseUnitTest {
    @InjectMocks
    private GetArticleUseCase useCase;
    @Mock
    private IArticleRepository repository;
    Long articleId = 1L;


    @Test
    public void GetArticle_ShouldReturnArticle_WithValidId() {
        Article article = new Article();
        article.setContent("Test content");
        article.setTitle("Unit test");

        doReturn(Optional.of(article)).when(repository).findById(articleId);

        GetArticleUseCase.InputValues inputValues = new GetArticleUseCase.InputValues(articleId);
        GetArticleUseCase.OutputValues outputValues = useCase.execute(inputValues);

        assertThat(outputValues.article().getContent()).isEqualTo(article.getContent());
        assertThat(outputValues.article().getTitle()).isEqualTo(article.getTitle());
        verify(repository, times(1)).findById(articleId);
    }

    @Test
    public void GetArticle_ShouldThrownError_WhenDontFindArticle() {
        doThrow(new IllegalArgumentException("Article not found with id : " + articleId)).when(repository).findById(articleId);

        GetArticleUseCase.InputValues inputValues = new GetArticleUseCase.InputValues(articleId);

        assertThatThrownBy(() -> useCase.execute(inputValues))
                .isInstanceOf(IllegalArgumentException.class)
                        .hasMessageContaining("Article not found with id : " + articleId);
        verify(repository, times(1)).findById(articleId);
    }
}
