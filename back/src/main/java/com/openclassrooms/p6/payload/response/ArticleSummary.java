package com.openclassrooms.p6.payload.response;

import java.time.LocalDateTime;

public record ArticleSummary(
        int id,
        int userId,
        int articleId,
        String title,
        String description,
        LocalDateTime publicationDate) {
}