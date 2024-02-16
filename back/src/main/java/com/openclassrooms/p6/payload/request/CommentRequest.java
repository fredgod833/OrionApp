package com.openclassrooms.p6.payload.request;

import jakarta.validation.constraints.NotBlank;

public record CommentRequest(
        @NotBlank(message = "Article ID cannot be blank or null") Long articleId,
        @NotBlank(message = "Comment of the article cannot be blank or null") String comment) {

}