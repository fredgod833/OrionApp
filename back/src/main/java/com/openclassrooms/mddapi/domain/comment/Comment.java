package com.openclassrooms.mddapi.domain.comment;

import com.openclassrooms.mddapi.domain.article.Article;
import com.openclassrooms.mddapi.domain.user.User;

import java.util.Date;

public class  Comment {
    private Long id;
    private String content;
    private Article article;
    private User user;
    private Date date;

    public Comment(Long id, String content, Article article, User user, Date date) {
        this.id = id;
        this.content = content;
        this.article = article;
        this.user = user;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
