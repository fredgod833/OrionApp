package com.mddcore.usecases.dto;

import com.mddcore.domain.models.User;
import com.mddcore.domain.models.Subject;

public class SubscriptionDto {
    private Subject subject;
    private User user;

    public SubscriptionDto(Subject subject, User user) {
        this.subject = subject;
        this.user = user;
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
