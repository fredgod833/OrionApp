package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.exceptions.ResourceNotFoundException;
import com.openclassrooms.mddapi.models.Theme;
import com.openclassrooms.mddapi.repositories.ThemeRepository;
import com.openclassrooms.mddapi.services.implementation.ThemeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ThemeServiceImplTests {

    private ThemeRepository themeRepo;
    private ThemeServiceImpl themeService;

    @BeforeEach
    public void setUp() {
        themeRepo = mock(ThemeRepository.class);
        themeService = new ThemeServiceImpl(themeRepo);
    }

    @Test
    public void testGetAll() {
        Theme theme1 = new Theme();
        theme1.setId(1);
        theme1.setName("Java");
        theme1.setDescription("Theme description");
        Theme theme2 = new Theme();
        theme2.setId(2);
        theme2.setName("Java");
        theme2.setDescription("Theme description");
        List<Theme> themes = List.of(theme1, theme2);

        when(themeRepo.findAll()).thenReturn(themes);

        List<Theme> retrievedThemes = themeService.getAll();

        verify(themeRepo, times(1)).findAll();
        assertEquals(themes, retrievedThemes);
    }

    @Test
    public void testGetAll_ThemesNotFound() {
        when(themeRepo.findAll()).thenReturn(Collections.emptyList());

        List<Theme> retrievedThemes = themeService.getAll();

        verify(themeRepo, times(1)).findAll();
        assertEquals(Collections.emptyList(), retrievedThemes);
    }

    @Test
    public void testGetById() {
        int themeId = 1;
        Theme theme = new Theme();
        theme.setId(themeId);
        theme.setName("Java");
        theme.setDescription("Theme description");

        when(themeRepo.findById(themeId)).thenReturn(Optional.of(theme));

        Theme retrievedTheme = themeService.getById(themeId);

        verify(themeRepo, times(1)).findById(themeId);
        assertEquals(theme, retrievedTheme);
    }

    @Test
    public void testGetById_ThemesNotFound() {
        int themeId = 1;
        doThrow(ResourceNotFoundException.class).when(themeRepo).findById(themeId);

        assertThrows(ResourceNotFoundException.class, () -> themeService.getById(themeId));
        verify(themeRepo, times(1)).findById(themeId);
    }

    @Test
    public void testGetByName() {
        String name = "Java";
        Theme theme = new Theme();
        theme.setId(1);
        theme.setName(name);
        theme.setDescription("Theme description");

        when(themeRepo.findByName(name)).thenReturn(Optional.of(theme));

        Theme retrievedTheme = themeService.getByName(name);

        verify(themeRepo, times(1)).findByName(name);
        assertEquals(theme, retrievedTheme);
    }

    @Test
    public void testGetByName_ThemesNotFound() {
        String name = "Java";
        doThrow(ResourceNotFoundException.class).when(themeRepo).findByName(name);

        assertThrows(ResourceNotFoundException.class, () -> themeService.getByName(name));
        verify(themeRepo, times(1)).findByName(name);
    }
}
