package com.openclassrooms.mddapi.models.payload.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PostRequest {

    @NotNull
    private int topicId;

    @Size(min = 3, max = 200)
    @NotNull
    private String title;

    @NotNull
    @Size(min = 3, max = 200)
    private String content;

}
