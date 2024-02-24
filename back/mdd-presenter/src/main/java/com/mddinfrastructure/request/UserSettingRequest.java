package com.mddinfrastructure.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public record UserSettingRequest(
        @NotNull @Email String email,
        @NotNull @Size(min = 8, max = 50) String password,
        @NotNull @Size(min = 3, max = 50) String username
) {}
