package com.openclassrooms.mddapi.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.openclassrooms.mddapi.dto.CommentDto;
import com.openclassrooms.mddapi.models.Comment;
import com.openclassrooms.mddapi.models.Post;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.repository.CommentRepository;
import com.openclassrooms.mddapi.repository.PostRepository;
import com.openclassrooms.mddapi.repository.UserRepository;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Transactional
    private CommentDto convertToDto(Comment comment) {
      CommentDto commentDto = new CommentDto();
      commentDto.setId(comment.getId());
      commentDto.setContent(comment.getContent());
      commentDto.setCreatedAt(comment.getCreatedAt());
      commentDto.setUpdatedAt(comment.getUpdatedAt());
      commentDto.setUserId(comment.getUser().getId());
      commentDto.setPostId(comment.getPost().getId());
      commentDto.setUsername(comment.getUser().getUsername());
      return commentDto;
    }


    private Comment convertToEntity(CommentDto commentDto) {
        Comment comment = new Comment();
        comment.setContent(commentDto.getContent());

        User user = userRepository.findById(commentDto.getUserId())
            .orElseThrow(() -> new RuntimeException("User not found for ID: " + commentDto.getUserId()));
        comment.setUser(user);

        Post post = postRepository.findById(commentDto.getPostId())
            .orElseThrow(() -> new RuntimeException("Post not found for ID: " + commentDto.getPostId()));
        comment.setPost(post);

        return comment;
    }

    public List<CommentDto> findAllCommentsByPostId(Long postId) {
        return commentRepository.findByPostId(postId).stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public CommentDto createComment(CommentDto commentDto) {
        Comment comment = convertToEntity(commentDto);
        Comment savedComment = commentRepository.save(comment);
        return convertToDto(savedComment);
    }

}
