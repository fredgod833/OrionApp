package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.models.Comment;
import com.openclassrooms.mddapi.repository.CommentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

  private final CommentRepository commentRepository;

  public CommentService(CommentRepository commentRepository) {
    this.commentRepository = commentRepository;
  }

  public void delete(Long id) {
    this.commentRepository.deleteById(id);
  }

  // par son id
  public Comment findById(Long id) {
    return this.commentRepository.findById(id).orElse(null);
  }

  public List<Comment> findAllByPost(Long postId) {
    return this.commentRepository.findAllByPostId(postId);
  }

  public Comment create(Comment comment) {
    return this.commentRepository.save(comment);
  }

}
