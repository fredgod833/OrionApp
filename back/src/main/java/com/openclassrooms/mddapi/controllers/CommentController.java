package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.dtos.CommentDTO;
import com.openclassrooms.mddapi.mappers.CommentMapper;
import com.openclassrooms.mddapi.models.Comment;
import com.openclassrooms.mddapi.services.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/post")
public class CommentController {

    private final CommentService commentService;
    private final CommentMapper commentMapper;

    public CommentController(CommentService commentService, CommentMapper commentMapper) {
        this.commentService = commentService;
        this.commentMapper = commentMapper;
    }

    @GetMapping("/{id}/comments")
    public ResponseEntity<List<CommentDTO>> getComments(@PathVariable int id) {
        List<Comment> comments = commentService.getComments(id);
        return ResponseEntity.ok(comments.stream().map(commentMapper::toDTO).toList());
    }

    @PostMapping("/{id}")
    public ResponseEntity<CommentDTO> saveComment(
            @PathVariable("id") Integer id,
            @RequestBody CommentDTO commentDTO,
            Principal principal) {
        Comment comment = commentMapper.toEntity(id, commentDTO, principal.getName());
        return ResponseEntity.ok(commentMapper.toDTO(commentService.saveComment(comment)));
    }
}
