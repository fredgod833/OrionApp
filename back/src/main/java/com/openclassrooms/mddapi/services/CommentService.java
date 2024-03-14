package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.models.Comment;

import java.util.List;

public interface CommentService {

    /**
     * @param id
     * @return
     */
    List<Comment> getComments(Integer id);

    /**
     *
     * @param comment
     */
    Comment saveComment(Comment comment);
}
