package com.mdddetails.mapper;

import com.mddcore.domain.models.Comment;
import com.mdddetails.models.CommentEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommentMapper extends EntMapper<Comment, CommentEntity> {
}
