package com.mddcore.usecases.ut.comment;

import com.mddcore.usecases.UseCaseExecutor;
import com.mddcore.usecases.comment.CreateCommentUseCase;
import com.mddcore.usecases.comment.GetAllCommentUseCase;
import com.mddinfrastructure.comment.CommentController;
import com.mddinfrastructure.request.CommentRequest;
import com.mddinfrastructure.response.ApiResponse;
import com.mddinfrastructure.response.CommentResponse;
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

@ExtendWith(MockitoExtension.class)
public class CommentControllerUnitTest {
    @InjectMocks
    public CommentController commentController;
    @Mock
    public UseCaseExecutor useCaseExecutor;
    @Mock
    public GetAllCommentUseCase getAllCommentUseCase;
    @Mock
    public CreateCommentUseCase createCommentUseCase;

    CommentRequest commentRequest = new CommentRequest("test", "theo", 1L);

    @Test
    public void GetAllComment_ShouldReturnListOfCommentResponse() {
        CommentResponse commentResponse = new CommentResponse("theo", "this is a test");
        List<CommentResponse> commentResponseList = new ArrayList<>();
        commentResponseList.add(commentResponse);

        doReturn(CompletableFuture.completedFuture(commentResponseList))
                .when(useCaseExecutor)
                .execute(eq(getAllCommentUseCase),
                        any(GetAllCommentUseCase.InputValues.class),
                        any());

        List<CommentResponse> resultList = commentController.getComments().join();

        assertThat(resultList).isEqualTo(commentResponseList);
    }

    @Test
    public void create_ShouldReturnSuccessApiResponse() {
        ApiResponse apiResponse = new ApiResponse(true, "Create Comment successfully");

        doReturn(CompletableFuture.completedFuture(apiResponse))
                .when(useCaseExecutor)
                .execute(eq(createCommentUseCase),
                        any(CreateCommentUseCase.InputValues.class),
                        any());

        ApiResponse response = commentController.create(commentRequest).join();

        assertThat(response).isEqualTo(apiResponse);
    }

    @Test
    public void create_ShouldReturnFalseInApiResponse() {
        ApiResponse apiResponse = new ApiResponse(false, "Already Commented");

        doReturn(CompletableFuture.completedFuture(apiResponse))
                .when(useCaseExecutor)
                .execute(eq(createCommentUseCase),
                        any(CreateCommentUseCase.InputValues.class),
                        any());

        ApiResponse response = commentController.create(commentRequest).join();

        assertThat(response).isEqualTo(apiResponse);
    }
}
