package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.exceptions.ResourceNotFoundException;
import com.openclassrooms.mddapi.models.Theme;

import java.util.List;

public interface ThemeService {

    /**
     * Retrieves all available themes.
     *
     * @return A {@link Theme} list containing all available themes.
     *         If no themes are found, an empty list is returned.
     */
    List<Theme> getAll();

    /**
     * Retrieves a {@link Theme} object based on its unique identifier.
     *
     * @param id The unique identifier of the Theme object to retrieve.
     * @return The Theme object with the matching ID, or null if no theme is found.
     * @throws ResourceNotFoundException If a theme with the provided ID is not found.
     */
    Theme getById(int id);

    /**
     * Retrieves a {@link Theme} object by its name.
     *
     * @param name The name of the theme to search for.
     * @return The {@link Theme} object with the matching name, or null if no theme is found.
     * @throws IllegalArgumentException If the provided name is null or empty.
     * @throws ResourceNotFoundException If a theme with the provided ID is not found.
     */
    Theme getByName(String name);
}
