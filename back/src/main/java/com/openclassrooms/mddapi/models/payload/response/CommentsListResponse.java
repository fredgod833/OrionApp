package com.openclassrooms.mddapi.models.payload.response;

import com.openclassrooms.mddapi.models.dto.CommentDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Collection;

/**
 * Format de message d'echange Rest pour l'obtention d'une liste de commentaire
 */
@Data
@AllArgsConstructor
public class CommentsListResponse {

    private Collection<CommentDto> comments;

}
