package com.openclassrooms.mddapi.application.subscription.dto;

import com.openclassrooms.mddapi.domain.subject.Subject;
import com.openclassrooms.mddapi.domain.user.User;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class SubscriptionDto {
    @NotNull
    private Subject subject;
    @NotNull
    private User user;
}
