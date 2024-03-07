package com.openclassrooms.mddapi.services.implementation;

import com.openclassrooms.mddapi.models.Theme;
import com.openclassrooms.mddapi.repositories.ThemeRepository;
import com.openclassrooms.mddapi.responses.ThemesResponse;
import com.openclassrooms.mddapi.services.ThemeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ThemeServiceImpl implements ThemeService {

    private final ThemeRepository themeRepo;

    public ThemeServiceImpl(ThemeRepository themeRepo) {
        this.themeRepo = themeRepo;
    }

    public ThemesResponse getAll() {
        List<Theme> themes = themeRepo.findAll();
        return new ThemesResponse(themes);
    }
}
