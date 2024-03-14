package com.mddcore.usecases.ut.article;

import com.mddcore.domain.models.Article;
import com.mddcore.domain.repository.IArticleRepository;
import com.mddcore.usecases.article.GetAllArticleUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GetAllArticleUseCaseUnitTest {
    @InjectMocks
    private GetAllArticleUseCase useCase;
    @Mock
    private IArticleRepository repository;

    @Test
    public void GetAllArticle_ShouldReturnListOfArticle() {
        Article article = new Article();
        article.setContent("Test content");
        List<Article> articleList = new ArrayList<>();
        articleList.add(article);

        doReturn(articleList).when(repository).findAll();

        GetAllArticleUseCase.InputValues inputValues = new GetAllArticleUseCase.InputValues();
        GetAllArticleUseCase.OutputValues outputValues = useCase.execute(inputValues);

        assertThat(outputValues.articleList().get(0).getContent()).isEqualTo(article.getContent());
        verify(repository, times(1)).findAll();
    }
}
