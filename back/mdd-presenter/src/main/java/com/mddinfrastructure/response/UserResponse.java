package com.mddinfrastructure.response;

import com.mddcore.domain.models.Subscription;

import java.util.List;

public record UserResponse(
        String email,
        String username,
        List<Subscription> subscriptionList
) {
}
