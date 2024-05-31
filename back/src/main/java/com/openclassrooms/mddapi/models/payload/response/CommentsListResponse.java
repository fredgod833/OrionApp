package com.openclassrooms.mddapi.models.payload.response;

import com.openclassrooms.mddapi.models.dto.CommentDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Collection;

@Data
@AllArgsConstructor
public class CommentsListResponse {

    private Collection<CommentDto> comments;

}
