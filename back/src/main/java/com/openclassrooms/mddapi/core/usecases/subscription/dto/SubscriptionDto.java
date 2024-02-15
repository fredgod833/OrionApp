package com.openclassrooms.mddapi.core.usecases.subscription.dto;

import com.openclassrooms.mddapi.core.domain.models.Subject;
import com.openclassrooms.mddapi.core.domain.models.User;
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
