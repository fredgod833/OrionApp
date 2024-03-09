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
    private Long userId;

    public static Subscription newInstance(Subject subject, Long userId) {
        return new Subscription(
                null,
                subject,
                userId
        );
    }
}
