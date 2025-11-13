package com.openclassrooms.mddapi.models.payload.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserRequest {

    @Size(max = 50)
    @Email
    private String email;

    @Size(max = 30)
    private String login;

    @NotBlank
    @Size(min = 1, max = 40)
    private String password;


}
