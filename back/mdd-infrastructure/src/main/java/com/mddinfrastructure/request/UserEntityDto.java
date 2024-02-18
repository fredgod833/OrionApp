package com.mddinfrastructure.request;

import com.mddcore.usecases.subscription.dto.SubscriptionDto;

import java.util.List;

public class UserEntityDto {
    private Long id;
    private String email;
    private String username;
    private String password;
    private List<SubscriptionDto> subscriptionList;

    public UserEntityDto(Long id, String email, String username, String password, List<SubscriptionDto> subscriptionList) {
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

    public List<SubscriptionDto> getSubscriptionList() {
        return subscriptionList;
    }

    public void setSubscriptionList(List<SubscriptionDto> subscriptionList) {
        this.subscriptionList = subscriptionList;
    }
}
