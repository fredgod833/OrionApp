package com.openclassrooms.mddapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.LocalDateTime;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TopicDto {
    private Long id;

    @NonNull
    @Size(max = 50)
    @Email
    private String title;

    @NonNull
    @Size(max = 500)
    private String content;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
