package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.responses.ThemesResponse;

public interface ThemeService {

    /**
     * Retrieves all themes.
     *
     * @return A {@link ThemesResponse} that contains a list themes.
     */
    ThemesResponse getAll();
}
