package com.mddcore.usecases.article;

import com.mddcore.domain.models.Article;
import com.mddcore.domain.repository.IArticleRepository;
import com.mddcore.usecases.UseCase;

import java.util.List;

/**
 * Retrieves all articles from the repository.
 */
public class GetAllArticleUseCase extends UseCase<GetAllArticleUseCase.InputValues, GetAllArticleUseCase.OutputValues> {
    private final IArticleRepository articleRepository;

    public GetAllArticleUseCase(IArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    /**
     * Executes the use case to fetch all articles.
     * @param inputValues the (empty) input values for this use case
     * @return the output values containing a list of all articles
     */
    @Override
    public OutputValues execute(InputValues inputValues) {
        return new OutputValues(articleRepository.findAll());
    }

    public record InputValues() implements UseCase.InputValues {}
    public record OutputValues(List<Article> articleList) implements UseCase.OutputValues {}
}
