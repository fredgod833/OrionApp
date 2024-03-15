package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.dtos.ThemeDTO;
import com.openclassrooms.mddapi.services.ThemeService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/themes")
public class ThemeController {

    private final ThemeService themeService;
    private final ModelMapper modelMapper;

    public ThemeController(ThemeService themeService, ModelMapper modelMapper) {
        this.themeService = themeService;
        this.modelMapper = modelMapper;
    }

    @GetMapping()
    public ResponseEntity<List<ThemeDTO>> getAll() {
        return ResponseEntity.ok(
                themeService.getAll().stream()
                        .map(theme -> modelMapper.map(theme, ThemeDTO.class))
                        .collect(Collectors.toList()));
    }
}
