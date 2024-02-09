package com.openclassrooms.mddapi.application.comment;

import com.openclassrooms.mddapi.application.EntityMapper;
import com.openclassrooms.mddapi.application.comment.dto.CommentDto;
import com.openclassrooms.mddapi.domain.comment.Comment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommentMapper extends EntityMapper<Comment, CommentDto> {
}
