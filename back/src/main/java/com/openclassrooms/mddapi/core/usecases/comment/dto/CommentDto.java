package com.openclassrooms.mddapi.core.usecases.comment.dto;

import com.openclassrooms.mddapi.core.domain.models.Article;
import com.openclassrooms.mddapi.core.domain.models.User;
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
