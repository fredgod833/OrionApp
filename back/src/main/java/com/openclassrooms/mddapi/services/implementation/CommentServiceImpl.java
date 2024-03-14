package com.openclassrooms.mddapi.services.implementation;


import com.openclassrooms.mddapi.models.Comment;
import com.openclassrooms.mddapi.repositories.CommentRepository;
import com.openclassrooms.mddapi.services.CommentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepo;

    public CommentServiceImpl(CommentRepository commentRepo) {
        this.commentRepo = commentRepo;
    }

    @Override
    public List<Comment> getComments(Integer id) {
        return commentRepo.getAllByPostId(id);
    }

    @Override
    public Comment saveComment(Comment comment) {
        return commentRepo.save(comment);
    }
}
