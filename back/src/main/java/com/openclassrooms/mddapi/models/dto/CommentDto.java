package com.openclassrooms.mddapi.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO Commentaire
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {

    private Integer id;

    private Integer postId;

    private UserDto user;

    private String content;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
