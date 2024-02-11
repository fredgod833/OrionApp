package com.openclassrooms.p6.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.p6.model.Comments;
import com.openclassrooms.p6.payload.request.CommentRequest;
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
     * Retrieve all comments.
     *
     * @return A collection of all comments as a list.
     */
    public List<Comments> getComments() {
        return commentRepository.findAll();
    }

    /**
     * Retrieves all the comments related to an article by their ID.
     *
     * @param id The identifier of the article.
     * @return A list of all the comments related to the article
     */
    public List<Comments> getAllCommentsByArticleId(final Long id) {
        return commentRepository.findAllByArticleId(id);
    }

    /**
     * Creates a comment for an article.
     *
     * @param userId   User ID of the creator of the comment.
     * @param comments The comments to be created for an article.
     * @return The saved or updated comments.
     */
    public Comments createComment(CommentRequest comments, Long userId) {
        Comments newComments = new Comments();

        newComments.setArticleId(comments.articleId());
        newComments.setUserId(userId);
        newComments.setComment(comments.comment());

        return commentRepository.save(newComments);
    }
}
