package com.openclassrooms.p6.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.p6.model.Comments;
import com.openclassrooms.p6.repository.CommentRepository;

import lombok.Data;

@Data
@Service
public class CommentsService {

    /**
     * 
     * Comments repo to perform database operations on the {@link Comments} entity.
     */
    @Autowired
    private CommentRepository commentRepository;

    /**
     * Retrieve all articles.
     *
     * @return A collection of all articles as a list.
     */
    public List<Comments> getComments() {
        return commentRepository.findAll();
    }

    /**
     * Retrieves an article by its unique identifier.
     *
     * @param id The identifier of the article.
     * @return An Optional containing the article if found, or empty if not.
     */
    public Optional<Comments> getAllCommentsByArticleId(final Long id) {
        return commentRepository.findById(id);
    }
}
