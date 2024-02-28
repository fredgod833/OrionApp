package com.mddcore.domain.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@EqualsAndHashCode
@Data
@NoArgsConstructor
public class Article {
    private Long id;
    private Subject subject;
    private String title;
    private String content;
    private Date date;
    private User user;
    private List<Comment> commentsList;

    public static Article newInstance(Subject subject, String title, String content, Date date, User user, List<Comment> commentsList) {
        return new Article(
                null,
                subject,
                title,
                content,
                date,
                user,
                commentsList
        );
    }
}
