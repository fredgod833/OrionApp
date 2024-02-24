package com.mddcore.domain.models;

public class Subscription {
    private Identity id;
    private Subject subject;
    private User user;

    public Subscription(Identity id, Subject subject, User user) {
        this.id = id;
        this.subject = subject;
        this.user = user;
    }

    public void setId(Identity id) {
        this.id = id;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Identity getId() {
        return id;
    }

    public Subject getSubject() {
        return subject;
    }

    public User getUser() {
        return user;
    }
}
