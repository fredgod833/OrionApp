package com.openclassrooms.mddapi.dto;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.openclassrooms.mddapi.models.Topic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;

    @NonNull
    @Size(max = 50)
    @Email
    private String email;

    @NonNull
    @Size(max = 20)
    private String name;

    @JsonIgnore
    @Size(max = 120)
    private String password;

    @NonNull
    private Long authLevel;

    private List<Topic> topics;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
