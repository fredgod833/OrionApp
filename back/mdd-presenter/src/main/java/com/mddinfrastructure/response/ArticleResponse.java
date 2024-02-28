package com.mddinfrastructure.response;

import com.mddcore.domain.models.Article;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public record ArticleResponse(
        Long id,
        String title,
        Date date,
        String authorName,
        String SubjectName,
        String content,
        List<CommentResponse> commentResponses
) {

    public static ArticleResponse from(Article article) {
        return new ArticleResponse(
                article.getId(),
                article.getTitle(),
                article.getDate(),
                article.getUser().getUsername(),
                article.getSubject().getName(),
                article.getContent(),
                CommentResponse.from(article.getCommentsList())
        );
    }

    public static List<ArticleResponse> from(List<Article> articleList) {
        return articleList.stream()
                .map(ArticleResponse::from)
                .collect(Collectors.toList());
    }
}
