package com.openclassrooms.mddapi.dtos;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PostDTO {
    int id;
    String title;
    String content;
    String author;
    String theme;
    LocalDateTime createdAt;
}
