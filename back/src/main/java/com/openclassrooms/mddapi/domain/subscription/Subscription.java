package com.openclassrooms.mddapi.domain.subscription;

import com.openclassrooms.mddapi.domain.subject.Subject;
import com.openclassrooms.mddapi.domain.user.User;

public class Subscription {
    private Long id;
    private Subject subject;
    private User user;

    public Subscription(Subject subject, User user) {
        this.subject = subject;
        this.user = user;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
