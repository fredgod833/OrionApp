package com.mddinfrastructure.response;

import com.mddcore.domain.models.User;

import java.util.List;
import java.util.stream.Collectors;

public record UserResponse(
        String email,
        String username,
        List<SubscriptionResponse> subscription
) {
    public static UserResponse from(User user) {
        List<SubscriptionResponse> subscriptionResponses = user.getSubscriptionList().stream()
                .map(SubscriptionResponse::from)
                .collect(Collectors.toList());
        return new UserResponse(
                user.getEmail(),
                user.getUsername(),
                subscriptionResponses
        );
    }
}
