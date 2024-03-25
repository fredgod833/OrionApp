package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.dtos.CommentDTO;
import com.openclassrooms.mddapi.mappers.CommentMapper;
import com.openclassrooms.mddapi.models.Comment;
import com.openclassrooms.mddapi.services.CommentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.security.Principal;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CommentControllerTests {

    private CommentController controller;
    private CommentService commentService;
    private CommentMapper commentMapper;

    @BeforeEach
    public void setUp() {
        commentService = mock(CommentService.class);
        commentMapper = mock(CommentMapper.class);
        controller = new CommentController(commentService, commentMapper);
    }

    @Test
    public void testGetComments() {
        int postId = 1;
        Comment comment1 = new Comment();
        comment1.setContent("Content 1");
        Comment comment2 = new Comment();
        comment2.setContent("Content 2");

        List<Comment> comments = List.of(comment1, comment2);
        when(commentService.getComments(postId)).thenReturn(comments);

        ResponseEntity<List<CommentDTO>> response = controller.getComments(postId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(comments.size(), response.getBody().size());
    }

    @Test
    public void testSaveComment() {
        int postId = 1;
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setContent("Content 1");
        Comment savedComment = new Comment();
        savedComment.setContent("Content 1");
        Principal principal = () -> "username";

        when(commentMapper.toEntity(anyInt(), any(CommentDTO.class), anyString())).thenReturn(savedComment);
        when(commentService.saveComment(savedComment)).thenReturn(savedComment);
        when(commentMapper.toDTO(savedComment)).thenReturn(commentDTO);

        ResponseEntity<CommentDTO> response = controller.saveComment(postId, commentDTO, principal);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(savedComment.getContent(), Objects.requireNonNull(response.getBody()).getContent());
    }
}
