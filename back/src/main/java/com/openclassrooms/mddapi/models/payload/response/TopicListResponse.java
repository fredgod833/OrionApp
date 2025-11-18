package com.openclassrooms.mddapi.models.payload.response;

import com.openclassrooms.mddapi.models.dto.TopicDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Collection;

/**
 * Format de message d'echange Rest pour l'obtention d'une liste de themes
 */
@Data
@AllArgsConstructor
public class TopicListResponse {

    private Collection<TopicDto> topics;

}
