package com.openclassrooms.mddapi.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {
    private Long id;

    @NonNull
    private PostLtdDto post;

    @NonNull
    private UserLtdDto user;

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
