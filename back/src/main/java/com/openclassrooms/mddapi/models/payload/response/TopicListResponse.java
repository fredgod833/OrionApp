package com.openclassrooms.mddapi.models.payload.response;

import com.openclassrooms.mddapi.models.dto.TopicDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Collection;

@Data
@AllArgsConstructor
public class TopicListResponse {

    private Collection<TopicDto> topics;

}
