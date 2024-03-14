package com.mddcore.usecases.ut.comment;

import com.mddcore.domain.models.Comment;
import com.mddcore.domain.repository.ICommentRepository;
import com.mddcore.usecases.comment.GetAllCommentUseCase;
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
public class GetAllCommentUseCaseUnitTest {
    @InjectMocks
    private GetAllCommentUseCase useCase;
    @Mock
    private ICommentRepository repository;

    @Test
    public void GetAllComment_ShouldReturnListOfComments() {
        Comment comment = new Comment();
        comment.setAuthor("theo");
        comment.setId(1L);
        List<Comment> commentList = new ArrayList<>();
        commentList.add(comment);

        doReturn(commentList).when(repository).findAll();

        GetAllCommentUseCase.OutputValues outputValues = useCase.execute(new GetAllCommentUseCase.InputValues());

        assertThat(outputValues.commentList().get(0).getAuthor()).isEqualTo(comment.getAuthor());
        assertThat(outputValues.commentList().get(0).getId()).isEqualTo(comment.getId());
        verify(repository, times(1)).findAll();
    }
}
