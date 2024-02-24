package com.mddcore.domain.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@AllArgsConstructor
@EqualsAndHashCode
@Data
@NoArgsConstructor
public class User {
    private Identity id;
    private String email;
    private String username;
    private String password;
    private String picture;
    private List<Subscription> subscriptionList = new ArrayList<>();

    public static User newInstance(String email, String username, String password, String picture, List<Subscription> subscriptionList) {
        return new User(
                Identity.nothing(),
                email,
                username,
                password,
                picture,
                subscriptionList
        );
    }
}