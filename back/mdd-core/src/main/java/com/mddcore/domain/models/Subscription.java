package com.mddcore.domain.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@EqualsAndHashCode
@Data
@NoArgsConstructor
public class Subscription {
    private Long id;
    private Subject subject;
    private User user;

    public static Subscription newInstance(Subject subject, User user) {
        return new Subscription(
                null,
                subject,
                user
        );
    }
}
