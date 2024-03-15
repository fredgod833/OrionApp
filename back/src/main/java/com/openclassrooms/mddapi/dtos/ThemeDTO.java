package com.openclassrooms.mddapi.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ThemeDTO {
    Integer id;

    @NotEmpty
    @Size(max = 60)
    String name;

    @NotEmpty
    @Size(max = 2500)
    String description;
}
