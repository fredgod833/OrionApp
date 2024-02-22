package com.mddcore.usecases.mapper;

import com.mddcore.domain.models.Comment;
import com.mddcore.usecases.dto.CommentDto;
import org.mapstruct.Mapper;

@Mapper
public interface CommentMapper extends EntityMapper<Comment, CommentDto> {
}
