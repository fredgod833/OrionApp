package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.dtos.PostDTO;
import com.openclassrooms.mddapi.exceptions.ResourceNotFoundException;
import com.openclassrooms.mddapi.exceptions.UserNotFoundException;
import com.openclassrooms.mddapi.models.Post;

import java.util.List;

public interface PostService {

    /**
     * Retrieves all posts associated with a given email address.
     *
     * @param email The email address of the user whose posts are to be retrieved.
     * @return A list of {@link Post} objects associated with the provided email address.
     *         If no posts are found, an empty list is returned.
     * @throws UserNotFoundException If user with provided email does not exist.
     */
    List<Post> getAll(String email);

    /**
     * Saves a new post associated with the provided email address.
     *
     * @param email   The email address of the user creating the post.
     * @param postDTO A DTO containing the details of the post to be saved.
     * @return The saved post object.
     */
    Post save(String email, PostDTO postDTO);

    /**
     * Retrieves a post based on its unique identifier.
     *
     * @param id the unique identifier of the post to retrieve.
     * @return the {@link Post} object representing the retrieved post,
     *         or null if no post is found.
     * @throws ResourceNotFoundException If a post with the provided ID is not found.
     */
    Post get(int id);
}
