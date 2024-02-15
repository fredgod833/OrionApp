package com.openclassrooms.mddapi.infrastructure.request;

import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
public class RegisterRequest {
    @NotNull
    @Email
    String email;
    @NotNull
    @Size(min = 3, max = 50)
    private String username;
    @NotNull
    @Size(min = 8, max = 80)
    private String password;
}
