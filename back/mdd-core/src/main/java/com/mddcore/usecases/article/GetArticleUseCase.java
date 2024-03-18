package com.mddcore.usecases.article;

import com.mddcore.domain.models.Article;
import com.mddcore.domain.repository.IArticleRepository;
import com.mddcore.usecases.UseCase;

/**
 * Use case for retrieving a single article by its ID.
 */
public class GetArticleUseCase extends UseCase<GetArticleUseCase.InputValues, GetArticleUseCase.OutputValues> {
    private final IArticleRepository articleRepository;

    public GetArticleUseCase(IArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    /**
     * Fetches an article by its ID, or throws an exception if not found.
     * @param input the input values containing the ID of the article to retrieve
     * @return the output values containing the retrieved article
     * @throws IllegalArgumentException if an article with the given ID cannot be found
     */
    @Override
    public OutputValues execute(InputValues input) {
        Article article = articleRepository.findById(input.id())
                .orElseThrow(() -> new IllegalArgumentException("Article not found with id : " + input.id()));

        return new OutputValues(article);
    }

    public record InputValues(Long id) implements UseCase.InputValues {}


    public record OutputValues(Article article) implements UseCase.OutputValues {}
}
