package com.openclassrooms.mddapi.application.comment.dto;

import com.openclassrooms.mddapi.domain.article.Article;
import com.openclassrooms.mddapi.domain.user.User;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class CommentDto {
    private String content;
    private Article article;
    private User user;
    private Date date;
}
