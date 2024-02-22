package com.mddcore.usecases.dto;

import com.mddcore.domain.models.Subscription;

import java.util.List;

public class UserDto {
    private String email;
    private String username;
    private String password;
    private List<Subscription> subscriptionList;

    public UserDto(String email, String username, String password, List<Subscription> subscriptionList) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.subscriptionList = subscriptionList;
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
