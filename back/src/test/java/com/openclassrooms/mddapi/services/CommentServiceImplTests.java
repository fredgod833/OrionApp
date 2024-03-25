package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.models.Comment;
import com.openclassrooms.mddapi.repositories.CommentRepository;
import com.openclassrooms.mddapi.services.implementation.CommentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CommentServiceImplTests {

    private CommentRepository commentRepo;
    private CommentService commentService;

    @BeforeEach
    void setUp() {
        commentRepo = mock(CommentRepository.class);
        commentService = new CommentServiceImpl(commentRepo);
    }

    @Test
    void testGetComments() {
        Integer postId = 1;
        Comment comment1 = new Comment();
        comment1.setId(1);
        comment1.setContent("This is a comment 1");
        Comment comment2 = new Comment();
        comment2.setId(2);
        comment2.setContent("This is a comment 2");
        List<Comment> comments = List.of(comment1, comment2);

        when(commentRepo.getAllByPostId(postId)).thenReturn(comments);

        List<Comment> retrievedComments = commentService.getComments(postId);

        verify(commentRepo, times(1)).getAllByPostId(postId);

        assertEquals(comments, retrievedComments);
    }

    @Test
    void testSaveComment() {
        Comment comment = new Comment();
        comment.setId(1);
        comment.setContent("This is a comment");

        when(commentRepo.save(comment)).thenReturn(comment);

        Comment savedComment = commentService.saveComment(comment);

        verify(commentRepo, times(1)).save(comment);

        assertEquals(comment, savedComment);
    }
}
