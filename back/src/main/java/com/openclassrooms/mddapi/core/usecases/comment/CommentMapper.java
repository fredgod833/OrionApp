package com.openclassrooms.mddapi.core.usecases.comment;

import com.openclassrooms.mddapi.core.usecases.EntityMapper;
import com.openclassrooms.mddapi.core.usecases.comment.dto.CommentDto;
import com.openclassrooms.mddapi.core.domain.models.Comment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommentMapper extends EntityMapper<Comment, CommentDto> {
}
