package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.dtos.RegisterDTO;
import com.openclassrooms.mddapi.dtos.UserDTO;
import com.openclassrooms.mddapi.exceptions.ResourceNotFoundException;
import com.openclassrooms.mddapi.exceptions.UserNotFoundException;
import com.openclassrooms.mddapi.models.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserService {
    //TODO java doc
    /**
     * Registers a new user based on the provided registration details.
     *
     * @param registerDTO The registration details.
     */
    void register(RegisterDTO registerDTO);


    /**
     * Retrieves user information for the specified user ID.
     *
     * @param id The unique identifier of the user to retrieve.
     * @return A {@link User} object containing user details.
     * @throws UserNotFoundException If user with provided ID does not exist.
     */
    User getUserById(int id);

    /**
     * Retrieves user details for the given email.
     *
     * @param email The user's email address.
     * @return A {@link User} object containing user details.
     * @throws UserNotFoundException If user with provided email does not exist.
     */
    User getByEmail(String email);

    /**
     * Updates a user with the specified information.
     *
     * @param userId  The ID of the user to update.
     * @param userDTO An {@link UserDTO} containing the updated user data.
     * @return An {@link User} representing the updated user details.
     * @throws UserNotFoundException If a user with the provided ID is not found.
     */
    User updateUser(int userId, UserDTO userDTO);

    /**
     * Checks if a user with the given email exists.
     *
     * @param email The email to check.
     * @return true If a user with the specified email exists; false otherwise.
     */
    boolean userExists(String email);

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
    User unsubscribeFromTheme(int id, String username);
}
