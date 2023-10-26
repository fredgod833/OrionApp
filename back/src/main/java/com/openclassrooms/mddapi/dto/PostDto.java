package com.openclassrooms.mddapi.dto;

import lombok.Data;
import java.util.Date;

@Data
public class PostDto {
    private Long id;
    private String title;
    private String content;
    private Date createdAt;
    private Date updatedAt;
    private Long userId;
    private Long topicId;
}
