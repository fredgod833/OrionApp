package com.mddinfrastructure.comment;

import com.mddcore.usecases.UseCaseExecutor;
import com.mddcore.usecases.comment.CreateCommentUseCase;
import com.mddcore.usecases.comment.GetAllCommentUseCase;
import com.mddinfrastructure.request.CommentRequest;
import com.mddinfrastructure.response.ApiResponse;
import com.mddinfrastructure.response.CommentResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component
public class CommentController implements CommentResource{
    private final UseCaseExecutor useCaseExecutor;
    private final GetAllCommentUseCase getAllCommentUseCase;
    private final CreateCommentUseCase createCommentUseCase;

    public CommentController(UseCaseExecutor useCaseExecutor,
                             GetAllCommentUseCase getAllCommentUseCase, CreateCommentUseCase createCommentUseCase) {
        this.useCaseExecutor = useCaseExecutor;
        this.getAllCommentUseCase = getAllCommentUseCase;
        this.createCommentUseCase = createCommentUseCase;
    }

    @Override
    public CompletableFuture<List<CommentResponse>> getComments() {
     return useCaseExecutor.execute(
                getAllCommentUseCase,
                new GetAllCommentUseCase.InputValues(),
                outputValues -> CommentResponse.from(outputValues.commentList()));
    }

    @Override
    public CompletableFuture<ApiResponse> create(@RequestBody CommentRequest request) {
        return useCaseExecutor.execute(
                createCommentUseCase,
                new CreateCommentUseCase.InputValues(new CreateCommentUseCase.InputRequest(
                        request.content(),
                        request.article_id(),
                        request.userName())),
                outputValues -> new ApiResponse(outputValues.success(), "Create Comment successfully")
        );
    }
}
