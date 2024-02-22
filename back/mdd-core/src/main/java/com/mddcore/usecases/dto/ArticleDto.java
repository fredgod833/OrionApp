package com.mddcore.usecases.dto;


import com.mddcore.domain.models.Comment;
import com.mddcore.domain.models.Subject;
import com.mddcore.domain.models.User;
import java.util.Date;
import java.util.List;

public class ArticleDto {
    private Subject subject;
    private String content;
    private User user;
    private Date date;
    private List<Comment> commentList;

    public ArticleDto(Subject subject, String content, User user, Date date, List<Comment> commentList) {
        this.subject = subject;
        this.content = content;
        this.user = user;
        this.date = date;
        this.commentList = commentList;
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

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }
}
