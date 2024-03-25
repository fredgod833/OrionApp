package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.dtos.ThemeDTO;
import com.openclassrooms.mddapi.models.Theme;
import com.openclassrooms.mddapi.services.ThemeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ThemeControllerTests {

    private ThemeController controller;
    private ThemeService themeService;
    @Autowired
    private ModelMapper modelMapper;

    @BeforeEach
    public void setUp() {
        this.modelMapper = mock(ModelMapper.class);
        this.themeService = mock(ThemeService.class);
        this.controller = new ThemeController(themeService, modelMapper);
    }

    @Test
    public void testGetAll_ReturnsListOfThemes() {
        Theme theme1 = new Theme();
        theme1.setId(1);
        theme1.setName("Test1");
        theme1.setDescription("Test1 description");
        Theme theme2 = new Theme();
        theme2.setId(2);
        theme2.setName("Test2");
        theme2.setDescription("Test2 description");
        List<Theme> themes = List.of(theme1, theme2);
        when(themeService.getAll()).thenReturn(themes);

        ResponseEntity<List<ThemeDTO>> response = controller.getAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(themes.size(), Objects.requireNonNull(response.getBody()).size());
    }


    @Test
    public void testGetAll_ReturnsEmptyList() {
        List<Theme> emptyList = Collections.emptyList();
        when(themeService.getAll()).thenReturn(emptyList);

        ResponseEntity<List<ThemeDTO>> response = controller.getAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(response.getBody(), Collections.emptyList());
    }
}
