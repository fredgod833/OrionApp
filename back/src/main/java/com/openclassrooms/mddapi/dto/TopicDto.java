package com.openclassrooms.mddapi.dto;

import lombok.Data;
import java.util.Date;

@Data
public class TopicDto {
    private Long id;
    private String name;
    private String description;
    private Date createdAt;
    private Date updatedAt;
}
