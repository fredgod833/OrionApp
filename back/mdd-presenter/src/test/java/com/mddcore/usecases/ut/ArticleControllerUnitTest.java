package com.mddcore.usecases.ut;

import com.mddcore.domain.models.Article;
import com.mddcore.usecases.UseCaseExecutor;
import com.mddcore.usecases.article.CreateArticleUseCase;
import com.mddcore.usecases.article.GetAllArticleUseCase;
import com.mddcore.usecases.article.GetArticleUseCase;
import com.mddinfrastructure.article.ArticleController;
import com.mddinfrastructure.request.ArticleRequest;
import com.mddinfrastructure.response.ApiResponse;
import com.mddinfrastructure.response.ArticleResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class ArticleControllerUnitTest {
    @InjectMocks
    private ArticleController articleController;
    @Mock
    private UseCaseExecutor useCaseExecutor;
    @Mock
    private CreateArticleUseCase createArticleUseCase;
    @Mock
    private GetAllArticleUseCase getAllArticleUseCase;
    @Mock
    private GetArticleUseCase getArticleUseCase;

    @Test
    public void GetAllArticle_ShouldReturnListOfArticlesResponses() {
        ArticleResponse articleResponse = mock(ArticleResponse.class);
        List<ArticleResponse> articleResponseList = new ArrayList<>();
        articleResponseList.add(articleResponse);

        doReturn(CompletableFuture.completedFuture(articleResponseList))
                .when(useCaseExecutor)
                .execute(eq(getAllArticleUseCase),
                        any(GetAllArticleUseCase.InputValues.class),
                        any());

        List<ArticleResponse> responses = articleController.getAllArticles().join();

        assertThat(responses).isEqualTo(articleResponseList);
    }

    @Test
    public void GetArticleById_ShouldReturnArticleResponse() {
        ArticleResponse articleResponse = mock(ArticleResponse.class);

        doReturn(CompletableFuture.completedFuture(articleResponse))
                .when(useCaseExecutor)
                .execute(eq(getArticleUseCase),
                        any(GetArticleUseCase.InputValues.class),
                        any());

        ArticleResponse responses = articleController.getArticleById(1L).join();

        assertThat(responses).isEqualTo(articleResponse);
    }

    @Test
    public void SaveArticle_ShouldReturnApiResponse() {
        ApiResponse apiResponse = new ApiResponse(true, "Article created");
        ArticleRequest articleRequest = mock(ArticleRequest.class);

        doReturn(CompletableFuture.completedFuture(apiResponse))
                .when(useCaseExecutor)
                .execute(eq(createArticleUseCase),
                        any(CreateArticleUseCase.InputValues.class),
                        any());

        ApiResponse responses = articleController.saveArticle(articleRequest).join();

        assertThat(responses).isEqualTo(responses);
    }
}
