package com.openclassrooms.mddapi.core.usecases.user.dto;

import com.openclassrooms.mddapi.core.domain.models.Subscription;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserDto {
    private String email;
    private String username;
    private String password;
    private List<Subscription> subscriptionList;
}
