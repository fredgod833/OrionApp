package com.openclassrooms.mddapi.models.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * DTO Theme
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TopicDto {

    private Integer id;

    private boolean subscribed = false;

    @NonNull
    @Size(max = 30)
    private String name;

    @NonNull
    @Size(max = 400)
    private String description;



}
