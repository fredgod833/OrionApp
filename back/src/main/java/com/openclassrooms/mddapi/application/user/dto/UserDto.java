package com.openclassrooms.mddapi.application.user.dto;

import com.openclassrooms.mddapi.domain.subscription.Subscription;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
public class UserDto {
    @NotNull
    @Email
    private String email;
    @NotNull
    @Size(min = 3, max = 50)
    private String username;
    @Size(min = 8, max = 80)
    private String password;
    private List<Subscription> subscriptionList;
}
