package com.mddcore.domain.repository;

import com.mddcore.domain.models.Comment;

import java.util.List;

public interface ICommentRepository {
    List<Comment> findAll();
    void save(Comment entity);
}
