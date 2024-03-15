package com.openclassrooms.mddapi.dtos;

import com.openclassrooms.mddapi.models.Theme;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDTO {
    Integer id;

    @NotEmpty
    @Size(max = 60)
    String email;

    @NotEmpty
    @Size(max = 30)
    String username;

    List<Theme> subscriptions;
}
