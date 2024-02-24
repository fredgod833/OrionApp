package com.mddcore.usecases.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record SignInRequest(
        @NotNull @Email String email,
        @NotNull @Size(min = 8, max = 50) String password) {
}
