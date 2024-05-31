package com.openclassrooms.mddapi.models.dto;

import java.time.LocalDateTime;

public class CommentDto {

    private Integer id;

    private Integer postId;

    private UserDto user;

    private String content;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
