package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.responses.ThemesResponse;
import com.openclassrooms.mddapi.services.ThemeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/themes")
public class ThemeController {

    private final ThemeService themeService;

    public ThemeController(ThemeService themeService) {
        this.themeService = themeService;
    }

    @GetMapping()
    public ThemesResponse getAll() {
        return themeService.getAll();
    }
}
