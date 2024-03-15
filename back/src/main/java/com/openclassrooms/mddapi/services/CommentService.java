package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.models.Comment;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public interface CommentService {

    /**
     * Retrieves a list of comments associated with a specific user identified by the provided ID.
     *
     * @param id the unique identifier of the entity for which to retrieve comments.
     * @return a list of {@link Comment} objects representing the comments associated with
     *         the specified entity, or an empty list if no comments are found.
     * @throws UsernameNotFoundException If a user with the provided username is not found.
     */
    List<Comment> getComments(Integer id);

    /**
     * Saves the provided comment.
     *
     * @param comment The comment object to be saved.
     * @return The saved comment object, or null if the save operation fails.
     */
    Comment saveComment(Comment comment);
}
