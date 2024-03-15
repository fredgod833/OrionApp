package com.openclassrooms.mddapi.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PostDTO {
    int id;

    @NotEmpty
    @Size(max = 100)
    String title;

    @NotEmpty
    @Size(max = 5000)
    String content;

    String author;
    String theme;
    LocalDateTime createdAt;
}
