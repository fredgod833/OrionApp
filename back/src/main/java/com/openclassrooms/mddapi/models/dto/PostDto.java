package com.openclassrooms.mddapi.models.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO Article
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {

    private Integer id;

    private UserDto author;

    private TopicDto topic;

    private String title;

    private String content;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
