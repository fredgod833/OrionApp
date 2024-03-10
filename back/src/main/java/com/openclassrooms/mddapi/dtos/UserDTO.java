package com.openclassrooms.mddapi.dtos;

import com.openclassrooms.mddapi.models.Theme;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDTO {
    int id;
    String email;
    String username;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    List<Theme> subscriptions;
}
