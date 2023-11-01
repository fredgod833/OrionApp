package com.openclassrooms.mddapi.dto;

import java.util.Date;

import lombok.Data;

@Data
public class CommentDto {
    private Long id;
    private String content;
    private Date createdAt;
    private Date updatedAt;
    private Long userId;
    private Long postId;
    private String username;
}
