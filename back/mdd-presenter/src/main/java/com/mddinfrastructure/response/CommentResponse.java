package com.mddinfrastructure.response;

import com.mddcore.domain.models.Comment;

import java.util.List;
import java.util.stream.Collectors;

public record CommentResponse(String username, String content) {
    public static CommentResponse from(Comment comment) {
        return new CommentResponse(
                comment.getAuthor(),
                comment.getContent()
        );
    }

    public static List<CommentResponse> from(List<Comment> commentList) {
        return commentList.stream()
                .map(CommentResponse::from)
                .collect(Collectors.toList()
                );
    }
}
