package com.openclassrooms.mddapi.models.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TopicDto {

    private Integer id;

    @NonNull
    @Size(max = 30)
    private String name;

    @NonNull
    @Size(max = 400)
    private String description;



}
