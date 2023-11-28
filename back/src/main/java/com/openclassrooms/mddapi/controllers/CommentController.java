package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.dto.CommentDto;
import com.openclassrooms.mddapi.mapper.CommentMapper;
import com.openclassrooms.mddapi.models.Comment;
import com.openclassrooms.mddapi.models.Post;
import com.openclassrooms.mddapi.models.User;

import java.util.List;


import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.mddapi.services.CommentService;
import com.openclassrooms.mddapi.services.PostService;
import com.openclassrooms.mddapi.services.UserService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/comments")
public class CommentController {
  private final PostService postService;

  private final UserService userService;

  private final CommentService commentService;

  private final CommentMapper commentMapper;

  public CommentController(PostService postService, CommentService commentService, CommentMapper commentMapper, UserService userService) {
    this.postService = postService;
    this.commentService = commentService;
    this.commentMapper = commentMapper;
    this.userService = userService;
  }

  // Tout les commentaire associé à un article ID
  @GetMapping("/post/{id}")
  public ResponseEntity<List<CommentDto>> findByArticleId(@PathVariable Long id) {
      Post post = this.postService.findById(id);

      if (post == null) {
          return ResponseEntity.notFound().build();
      }

      List<Comment> comments = this.commentService.findAllByPost(id);

      return ResponseEntity.ok().body(this.commentMapper.toDto(comments));
  }

  // Créer un commentaire associé à un article ID
  @PostMapping("")
  public ResponseEntity<CommentDto> create(@RequestBody Comment comment, Authentication authentication) {
      Post post = this.postService.findById(comment.getPost().getId());
      User user = this.userService.findByEmail(authentication.getName());

      if (post == null || user == null) {
          return ResponseEntity.notFound().build();
      }

      Comment newComment = new Comment();
      newComment.setPost(post);
      newComment.setContent(comment.getContent());
      newComment.setUser(user);

      Comment createdComment = this.commentService.create(newComment);
      return ResponseEntity.ok().body(this.commentMapper.toDto(createdComment));
  }
}
