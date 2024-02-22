package com.mddcore.usecases.dto;

import com.mddcore.domain.models.Article;
import com.mddcore.domain.models.User;

import java.util.Date;

public class CommentDto {
    private String content;
    private Article article;
    private User user;
    private Date date;

    public CommentDto(String content, Article article, User user, Date date) {
        this.content = content;
        this.article = article;
        this.user = user;
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
