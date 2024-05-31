package com.openclassrooms.mddapi.models.payload.response;

import com.openclassrooms.mddapi.models.dto.PostDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Collection;

@Data
@AllArgsConstructor
public class PostListResponse {

    private Collection<PostDto> posts;

}
