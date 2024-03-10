package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.dtos.RegisterDTO;
import com.openclassrooms.mddapi.dtos.UpdatedUserDTO;
import com.openclassrooms.mddapi.dtos.UserDTO;
import com.openclassrooms.mddapi.exceptions.UserNotFoundException;
import com.openclassrooms.mddapi.models.Theme;
import com.openclassrooms.mddapi.responses.ThemesResponse;

import java.util.List;
import java.util.Set;

public interface UserService {

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
     * @return A {@link UserDTO} object containing user details.
     * @throws UserNotFoundException If user with provided ID does not exist.
     */
    UserDTO getUserById(int id);

    /**
     * Retrieves user details for the given email.
     *
     * @param email The user's email address.
     * @return A {@link UserDTO} object containing user details.
     * @throws UserNotFoundException If user with provided email does not exist.
     */
    UserDTO getUserByEmail(String email);

    /**
     *
     * @param user
     * @return
     */
    UpdatedUserDTO updateUser(int userId, UpdatedUserDTO user);

    /**
     * Checks if a user with the given email exists.
     *
     * @param email The email to check.
     * @return true If a user with the specified email exists; false otherwise.
     */
    boolean userExists(String email);

    /**
     *
     * @param id
     * @param username
     */
    UserDTO subscribeToTheme(int id, String username);

    /**
     *
     * @param id
     * @param username
     */
    UserDTO unsubscribeFromTheme(int id, String username);
}
