package com.mddinfrastructure.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserUpdateDto {
    String email;
    String password;
    String username;
}
