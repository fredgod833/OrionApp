package com.openclassrooms.mddapi.models.payload.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * Format de message d'echange Rest pour les requetes de commentaire
 */
@Data
public class CommentRequest {
    @NotNull
    @Size(min = 3, max = 200, message="Le commentaire doit avoir moins de 200 caractères")
    private String comment;
}
