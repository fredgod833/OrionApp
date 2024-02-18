package com.mddcore.domain.models;

public class Subscription {
    private Long id;
    private Subject subject;
    private User user;

    public Subscription(Subject subject, User user) {
        this.subject = subject;
        this.user = user;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public Subject getSubject() {
        return subject;
    }

    public User getUser() {
        return user;
    }
}
