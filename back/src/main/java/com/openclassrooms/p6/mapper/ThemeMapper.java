package com.openclassrooms.p6.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.openclassrooms.p6.model.Themes;
import com.openclassrooms.p6.payload.response.SingleThemeResponse;

@Mapper(componentModel = "spring")
public interface ThemeMapper {
    // Map Themes entity list to MultipleThemesResponse DTO
    Iterable<SingleThemeResponse> toDtoThemes(List<Themes> themes);

    // Map Themes entity object to SingleThemeResponse DTO
    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "title", source = "title"),
            @Mapping(target = "description", source = "description")
    })

    SingleThemeResponse toDtoTheme(Themes themes);
}
