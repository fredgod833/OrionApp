package com.mddcore.domain.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@EqualsAndHashCode
@Data
@NoArgsConstructor
public class  Comment {
    private Long id;
    private String content;
    private Article article;
    private String author;

    public static Comment newInstance(String content, Article article, String author) {
        return new Comment(
                null,
                content,
                article,
                author
        );
    }
}
