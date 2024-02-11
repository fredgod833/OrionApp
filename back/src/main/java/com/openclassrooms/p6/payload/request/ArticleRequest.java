package com.openclassrooms.p6.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ArticleRequest(
                @NotNull Long themeId,
                @NotBlank(message = "Title cannot be blank or null") String title,
                @NotBlank(message = "Description cannot be blank or null") String description) {
}
