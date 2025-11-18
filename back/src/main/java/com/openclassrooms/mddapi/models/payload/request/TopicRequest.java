package com.openclassrooms.mddapi.models.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * Format de message d'echange Rest pour les requetes de themes
 */
@Data
public class TopicRequest {

    @NotBlank
    @Size(max = 30)
    private String name;

    @NotBlank
    @Size(max = 400)
    private String description;

}
