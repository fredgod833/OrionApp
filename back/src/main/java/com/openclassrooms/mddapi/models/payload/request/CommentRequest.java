package com.openclassrooms.mddapi.models.payload.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CommentRequest {

    @NotNull
    @Size(min = 3, max = 200)
    private String comment;

}
