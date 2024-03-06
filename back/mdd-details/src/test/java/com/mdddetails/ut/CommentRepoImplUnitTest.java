package com.mdddetails.ut;

import com.mddcore.domain.models.Comment;
import com.mdddetails.mapper.CommentMapper;
import com.mdddetails.models.CommentEntity;
import com.mdddetails.repository.comment.CommentJpaRepo;
import com.mdddetails.repository.comment.CommentRepoImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CommentRepoImplUnitTest {
    @InjectMocks
    private CommentRepoImpl commentRepo;
    @Mock
    private CommentJpaRepo jpaRepo;
    @Mock
    private CommentMapper commentMapper;

    @Test
    public void findAll_ShouldReturnListOfComments_WhenCommentsExist() {
        CommentEntity commentEntity1 = new CommentEntity();
        commentEntity1.setAuthor("test author 1");
        CommentEntity commentEntity2 = new CommentEntity();
        commentEntity2.setAuthor("test author 2");
        Comment comment1 = new Comment();
        comment1.setAuthor("test author 1");
        Comment comment2 = new Comment();
        comment2.setAuthor("test author 2");

        when(jpaRepo.findAll()).thenReturn(List.of(commentEntity1, commentEntity2));
        when(commentMapper.toDomain(commentEntity1)).thenReturn(comment1);
        when(commentMapper.toDomain(commentEntity2)).thenReturn(comment2);

        List<Comment> comments = commentRepo.findAll();

        assertThat(comments).containsExactly(comment1, comment2);
        verify(jpaRepo).findAll();
        verify(commentMapper, times(2)).toDomain(any(CommentEntity.class));
    }

    @Test
    public void save_ShouldPersistComment_WhenCommentIsValid() {
        Comment comment = new Comment();
        CommentEntity commentEntity = new CommentEntity();

        when(commentMapper.toEntity(comment)).thenReturn(commentEntity);
        when(jpaRepo.save(commentEntity)).thenReturn(commentEntity);

        commentRepo.save(comment);

        verify(jpaRepo).save(commentEntity);
        verify(commentMapper).toEntity(comment);
    }

}
