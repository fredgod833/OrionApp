package com.openclassrooms.mddapi.application.article.dto;

import com.openclassrooms.mddapi.domain.comment.Comment;
import com.openclassrooms.mddapi.domain.subject.Subject;
import com.openclassrooms.mddapi.domain.user.User;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class ArticleDto {
    @NotNull
    private Subject subject;
    @NotNull
    private String content;
    @NotNull
    private User user;
    private Date date;
    private List<Comment> commentList;
}
