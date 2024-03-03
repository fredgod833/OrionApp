package com.mddcore.usecases.comment;

import com.mddcore.domain.models.Article;
import com.mddcore.domain.models.Comment;
import com.mddcore.domain.repository.ICommentRepository;
import com.mddcore.usecases.UseCase;
import com.mddcore.usecases.article.GetArticleUseCase;

public class CreateCommentUseCase extends UseCase<CreateCommentUseCase.InputValues, CreateCommentUseCase.OutputValues> {
    private final ICommentRepository commentRepository;
    private final GetArticleUseCase getArticleUseCase;

    public CreateCommentUseCase(ICommentRepository commentRepository, GetArticleUseCase getArticleUseCase) {
        this.commentRepository = commentRepository;
        this.getArticleUseCase = getArticleUseCase;
    }

    @Override
    public OutputValues execute(InputValues input) {
        if (isAlreadyComment(input)) {
            return new OutputValues(false);
        }
        try {
            Comment comment = Comment.newInstance(
                    input.inputRequest.content(),
                    getArticle(input),
                    input.inputRequest.username()
            );
            commentRepository.save(comment);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error while creating new Article, cant be save : " + e);
        }
        return new OutputValues(true);
    }

    private Article getArticle(InputValues input) {
        GetArticleUseCase.InputValues inputValues = new GetArticleUseCase.InputValues(input.inputRequest.articleId());
        return getArticleUseCase.execute(inputValues).article();
    }

    private Boolean isAlreadyComment(InputValues input) {
        Article article = getArticle(input);
        return article.getCommentsList().stream().anyMatch(comment -> comment.getAuthor().equals(input.inputRequest.username()));
    }

    public record InputValues(InputRequest inputRequest) implements UseCase.InputValues {}

    public record OutputValues(Boolean success) implements UseCase.OutputValues {}

    public record InputRequest(String content, Long articleId, String username)  implements UseCase.InputRequest{}
}

