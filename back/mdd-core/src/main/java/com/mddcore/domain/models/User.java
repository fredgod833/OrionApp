package com.mddcore.domain.models;

import java.util.List;

public class User {
    private Long id;
    private String email;
    private String username;
    private String password;
    private List<Subscription> subscriptionList;

    public User(Long id, String email, String username, String password, List<Subscription> subscriptionList) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.password = password;
        this.subscriptionList = subscriptionList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Subscription> getSubscriptionList() {
        return subscriptionList;
    }

    public void setSubscriptionList(List<Subscription> subscriptionList) {
        this.subscriptionList = subscriptionList;
    }
}