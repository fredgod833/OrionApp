package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.models.Theme;

import java.util.List;

public interface ThemeService {
    //TODO  add java doc

    /**
     *
     */
    List<Theme> getAll();

    Theme getById(int id);

    /**
     * @param name
     * @return
     */
    Theme getByName(String name);
}
