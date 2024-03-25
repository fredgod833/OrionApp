package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.payload.requests.RegisterRequest;
import com.openclassrooms.mddapi.dtos.UserDTO;
import com.openclassrooms.mddapi.exceptions.ResourceNotFoundException;
import com.openclassrooms.mddapi.exceptions.UserNotFoundException;
import com.openclassrooms.mddapi.mappers.DoIgnore;
import com.openclassrooms.mddapi.models.User;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserService {
    /**
     * Registers a new user based on the provided registration details.
     *
     * @param registerRequest The registration details.
     * @throws DuplicateKeyException If username or email already exist.
     */
    void register(RegisterRequest registerRequest);


    /**
     * Retrieves user information for the specified user ID.
     *
     * @param id The unique identifier of the user to retrieve.
     * @return A {@link User} object containing user details.
     * @throws UserNotFoundException If user with provided ID does not exist.
     */
    User getById(int id);

    /**
     * Retrieves user details for the given email.
     *
     * @param email The user's email address.
     * @return A {@link User} object containing user details.
     * @throws UserNotFoundException If user with provided email does not exist.
     */
    User getByEmail(String email);

    /**
     * Retrieves a user object based on the provided email address.
     *
     * @param username the name of the user to retrieve.
     * @return A {@link User} with the matching email address.
     * @throws UserNotFoundException If user with provided email does not exist.
     */
    @DoIgnore
    User getByUsername(String username);

    /**
     * Updates a user with the specified information.
     *
     * @param userId  The ID of the user to update.
     * @param userDTO An {@link UserDTO} containing the updated user data.
     * @throws UserNotFoundException If a user with the provided ID is not found.
     * @throws DuplicateKeyException If identifier that need update already exist.
     */
    void update(int userId, UserDTO userDTO);

    /**
     * Checks if the provided email address does not exist in the system.
     *
     * @param email The email address to check. (Must not be null)
     * @return true if the email address does not exist.
     * @throws UserNotFoundException If a user with the provided email is not found.
     * @throws DuplicateKeyException If email already exist.
     */
    boolean emailDoesNotExist(String email);


    /**
     * Checks if the provided username does not exist in the system.
     *
     * @param username The username to check. (Must not be null)
     * @return true if the username does not exist.
     * @throws UserNotFoundException If a user with the provided username is not found.
     * @throws DuplicateKeyException If username already exist.
     */
    boolean usernameDoesNotExist(String username);

    /**
     * Subscribes a user to a theme.
     *
     * @param id       The ID of the theme to subscribe to.
     * @param username The username of the user subscribing.
     * @return A {@link User} representing the updated user with the subscribed theme.
     * @throws ResourceNotFoundException If a theme with the provided ID is not found.
     * @throws UsernameNotFoundException If a user with the provided username is not found.
     * @throws IllegalArgumentException  If the user is already subscribed to the theme.
     */
    User subscribeToTheme(int id, String username);

    /**
     * Unsubscribes a user from a theme.
     *
     * @param id The ID of the theme to unsubscribe from.
     * @param username The username of the user unsubscribing.
     * @return A {@link User} representing the updated user with the unsubscribed theme.
     * @throws ResourceNotFoundException If a theme with the provided ID is not found.
     * @throws UsernameNotFoundException If a user with the provided username is not found.
     * @throws IllegalArgumentException  If the user is not already subscribed to the theme.
     */
    User unSubscribeFromTheme(int id, String username);
}
