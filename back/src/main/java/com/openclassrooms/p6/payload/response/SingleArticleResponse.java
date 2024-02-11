package com.openclassrooms.p6.payload.response;

import java.util.List;

public record SingleArticleResponse(
        int id,
        String authorName,
        String creationDate,
        String theme,
        String title,
        String description,
        List<CommentResponse> comments) {

}