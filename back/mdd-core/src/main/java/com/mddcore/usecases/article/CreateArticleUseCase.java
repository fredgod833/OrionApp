package com.mddcore.usecases.article;

import com.mddcore.domain.models.Article;
import com.mddcore.domain.models.Subject;
import com.mddcore.domain.models.User;
import com.mddcore.domain.repository.IArticleRepository;
import com.mddcore.usecases.UseCase;
import com.mddcore.usecases.subject.GetSubjectUseCase;
import com.mddcore.usecases.user.GetUserUseCase;

/**
 * Handles the creation of articles, incorporating subject and user information.
 */
public class CreateArticleUseCase extends UseCase<CreateArticleUseCase.InputValues, CreateArticleUseCase.OutputValues> {
    private final IArticleRepository articleRepository;
    private final GetSubjectUseCase getSubjectUseCase;
    private final GetUserUseCase getUserUseCase;

    public CreateArticleUseCase(IArticleRepository articleRepository, GetSubjectUseCase getSubjectUseCase, GetUserUseCase getUserUseCase) {
        this.articleRepository = articleRepository;
        this.getSubjectUseCase = getSubjectUseCase;
        this.getUserUseCase = getUserUseCase;
    }

    /**
     * Executes the use case to create an article with the provided details.
     * @param input the input values containing the article details
     * @return the output values indicating success
     * @throws IllegalArgumentException if the article cannot be saved
     */
    @Override
    public OutputValues execute(InputValues input) {
        try {
            Article article = new Article(
                    null,
                    getSubject(input),
                    input.inputRequest.title(),
                    input.inputRequest.content(),
                    null,
                    getUser(input),
                    null
            );
            articleRepository.save(article);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error while creating new Article, cant be save : " + e);
        }
        return new OutputValues(true);
    }

    /**
     * Retrieves the subject based on the provided input.
     * @param input the input values
     * @return the found subject
     */
    private Subject getSubject(InputValues input) {
        GetSubjectUseCase.InputValues inputValues = new GetSubjectUseCase.InputValues(input.inputRequest.subject_id());
        return getSubjectUseCase.execute(inputValues).subject();
    }

    /**
     * Retrieves the user based on the provided input.
     * @param input the input values
     * @return the found user
     */
    private User getUser(InputValues input) {
        GetUserUseCase.InputValues inputValues = new GetUserUseCase.InputValues(input.inputRequest.user_id());
        return getUserUseCase.execute(inputValues).user();
    }

    public record InputValues(InputRequest inputRequest) implements UseCase.InputValues {}

    public record OutputValues(Boolean success) implements UseCase.OutputValues {}

    public record InputRequest(Long subject_id, Long user_id, String title, String content) implements UseCase.InputRequest {}
}
