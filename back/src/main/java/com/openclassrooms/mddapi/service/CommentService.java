package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.CommentDto;
import com.openclassrooms.mddapi.model.Comment;
import com.openclassrooms.mddapi.model.Post;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.CommentRepository;
import com.openclassrooms.mddapi.repository.PostRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

@Service
@Transactional
public class CommentService {
    @Autowired
    CommentRepository commentRepository;

    @Autowired
    PostRepository postRepository;

    public void saveComment(CommentDto commentDto, User user) {
        Post post = postRepository.findById(commentDto.getPost_id()).orElse(null);
        if (post != null) {
            Comment comment = new Comment();
            comment.setUser(user);
            comment.setPost(post);
            comment.setContent(commentDto.getContent());
            comment.setCreatedAt(OffsetDateTime.now());
            comment.setUpdatedAt(OffsetDateTime.now());
            commentRepository.save(comment);
        }
    }

    public List<Comment> getAllComments(Integer postId){
        return commentRepository.findByPostIds(postId);
    }
}