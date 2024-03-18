package com.mddinfrastructure.article;

import com.mddcore.usecases.UseCaseExecutor;
import com.mddcore.usecases.article.CreateArticleUseCase;
import com.mddcore.usecases.article.GetAllArticleUseCase;
import com.mddcore.usecases.article.GetArticleUseCase;
import com.mddinfrastructure.request.ArticleRequest;
import com.mddinfrastructure.response.ApiResponse;
import com.mddinfrastructure.response.ArticleResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Controller for managing articles.
 */
@Component
public class ArticleController implements ArticleResource {
    private final UseCaseExecutor useCaseExecutor;
    private final CreateArticleUseCase createArticleUseCase;
    private final GetAllArticleUseCase getAllArticleUseCase;
    private final GetArticleUseCase getArticleUseCase;

    public ArticleController(UseCaseExecutor useCaseExecutor, CreateArticleUseCase createArticleUseCase, GetAllArticleUseCase getAllArticleUseCase, GetArticleUseCase getArticleUseCase) {
        this.useCaseExecutor = useCaseExecutor;
        this.createArticleUseCase = createArticleUseCase;
        this.getAllArticleUseCase = getAllArticleUseCase;
        this.getArticleUseCase = getArticleUseCase;
    }

    /**
     * Retrieves all articles.
     * @return A CompletableFuture containing a list of ArticleResponse objects.
     */
    @Override
    public CompletableFuture<List<ArticleResponse>> getAllArticles() {
        return useCaseExecutor.execute(getAllArticleUseCase, new GetAllArticleUseCase.InputValues(), outputValues -> ArticleResponse.from(outputValues.articleList()));
    }

    /**
     * Retrieves an article by its ID.
     * @param id The ID of the article to retrieve.
     * @return A CompletableFuture containing an ArticleResponse object.
     */
    @Override
    public CompletableFuture<ArticleResponse> getArticleById(@PathVariable Long id) {
        return useCaseExecutor.execute(getArticleUseCase, new GetArticleUseCase.InputValues(id), outputValues -> ArticleResponse.from(outputValues.article()));
    }

    /**
     * Saves a new article.
     * @param articleRequest The request object containing information about the article.
     * @return A CompletableFuture containing an ApiResponse indicating the success of the operation.
     */
    @Override
    public CompletableFuture<ApiResponse> saveArticle(@RequestBody ArticleRequest articleRequest) {
        return useCaseExecutor.execute(createArticleUseCase, new CreateArticleUseCase.InputValues(new CreateArticleUseCase.InputRequest(articleRequest.subject_id(), articleRequest.user_id(), articleRequest.title(), articleRequest.content())), outputValues -> new ApiResponse(outputValues.success(), "Article created"));
    }
}
