package com.openclassrooms.mddapi.application.user.auth.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

public class LoginRequest {
    @NotNull
    @Email
    String email;
    @NotNull
    String password;
}
