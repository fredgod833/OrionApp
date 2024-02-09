package com.openclassrooms.mddapi.domain.article;

import com.openclassrooms.mddapi.domain.comment.Comment;
import com.openclassrooms.mddapi.domain.user.User;
import com.openclassrooms.mddapi.domain.subject.Subject;
import java.util.Date;
import java.util.List;

public class Article {
    private Long id;
    private Subject subject;
    private String content;
    private User user;
    private Date date;
    private List<Comment> commentsList;

    public Article(Long id, Subject subject, String content, User user, Date date, List<Comment> commentsList) {
        this.id = id;
        this.subject = subject;
        this.content = content;
        this.user = user;
        this.date = date;
        this.commentsList = commentsList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public List<Comment> getCommentsList() {
        return commentsList;
    }

    public void setCommentsList(List<Comment> commentsList) {
        this.commentsList = commentsList;
    }
}
