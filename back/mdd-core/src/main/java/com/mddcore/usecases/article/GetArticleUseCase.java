package com.mddcore.usecases.article;

import com.mddcore.domain.models.Article;
import com.mddcore.domain.repository.IArticleRepository;
import com.mddcore.usecases.UseCase;

public class GetArticleUseCase extends UseCase<GetArticleUseCase.InputValues, GetArticleUseCase.OutputValues> {
    private final IArticleRepository articleRepository;

    public GetArticleUseCase(IArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @Override
    public OutputValues execute(InputValues input) {
        Article article = articleRepository.findById(input.id()).orElse(null);
        if (article == null) {
            throw new IllegalArgumentException("Article not found with userId : " + input.id());
        }
        return new OutputValues(article);
    }

    public record InputValues(Long id) implements UseCase.InputValues {}


    public record OutputValues(Article article) implements UseCase.OutputValues {}
}
