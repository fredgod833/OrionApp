package com.openclassrooms.mddapi.responses;

import com.openclassrooms.mddapi.models.Theme;

import java.util.List;

public record ThemesResponse(List<Theme> themes) {
}
