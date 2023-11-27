package com.openclassrooms.mddapi.model.dto;

import lombok.*;

@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@Builder
public class UserDto {
    private int id_user;
    private String username;
    private String lastname;
    private String email;
}
