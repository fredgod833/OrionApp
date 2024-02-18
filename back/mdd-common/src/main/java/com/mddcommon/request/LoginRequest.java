package com.mddcommon.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginRequest {
    @NotNull
    @Email
    String email;
    @NotNull
    @Size(min = 8, max = 80)
    String password;
}
