package com.mddcore.usecases.comment;

import com.mddcore.domain.models.Comment;
import com.mddcore.usecases.EntityMapper;
import com.mddcore.usecases.comment.dto.CommentDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommentMapper extends EntityMapper<Comment, CommentDto> {
}
