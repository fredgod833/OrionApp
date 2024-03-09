package com.mddcore.domain.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@EqualsAndHashCode
@Data
@NoArgsConstructor
public class Subject {
    private Long id;
    private String name;
    private String description;
    private List<Article> articleList;
    private List<Subscription> subscriptionList;

    public static Subject newInstance(String name, String description, List<Article> articleList, List<Subscription> subscriptionList) {
        return new Subject(
                null,
                name,
                description,
                articleList,
                subscriptionList
        );
    }
}
