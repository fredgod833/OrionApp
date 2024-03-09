package com.mddinfrastructure.response;

import com.mddcore.domain.models.Subscription;

public record SubscriptionResponse(SubjectResponse subject) {
    public static SubscriptionResponse from(Subscription subscription) {
        SubjectResponse subjectResponse = SubjectResponse.from(subscription.getSubject());
        return new SubscriptionResponse(
                subjectResponse
        );
    }
}
