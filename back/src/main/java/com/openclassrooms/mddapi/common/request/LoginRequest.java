package com.openclassrooms.mddapi.common.request;

import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
public class LoginRequest {
    @NotNull
    @Email
    String email;
    @NotNull
    @Size(min = 8, max = 80)
    String password;
}
