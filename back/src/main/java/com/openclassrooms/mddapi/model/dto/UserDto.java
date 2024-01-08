package com.openclassrooms.mddapi.model.dto;

import com.openclassrooms.mddapi.model.Subscription;
import lombok.*;

@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@Builder
/**
 * Class dto set by user
 */
public class UserDto {
    private int id_user;
    private String username;
    private String email;
    Subscription subscription;
}
