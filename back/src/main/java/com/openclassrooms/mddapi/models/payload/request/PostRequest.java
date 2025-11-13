package com.openclassrooms.mddapi.models.payload.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PostRequest {

    @NotNull(message="Le thème de l'article est nécessaire.")
    private int topicId;

    @Size(min = 3, max = 200, message="Le titre de l'article doit être compris entre 3 et 200 caractères")
    @NotNull
    private String title;

    @NotNull
    @Size(min = 3, max = 2000, message="Le contenu de l'article doit être compris entre 3 et 2000 caractères")
    private String content;

}
