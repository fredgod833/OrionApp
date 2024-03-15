package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.dtos.ThemeDTO;
import com.openclassrooms.mddapi.services.ThemeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Operation(summary = "Get all themes", description = "Get a list of all available themes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns a list of ThemeDTO objects",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ThemeDTO.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)})
    public ResponseEntity<List<ThemeDTO>> getAll() {
        return ResponseEntity.ok(
                themeService.getAll().stream()
                        .map(theme -> modelMapper.map(theme, ThemeDTO.class))
                        .collect(Collectors.toList()));
    }
}
