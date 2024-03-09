package com.mdddetails.mapper;

import com.mddcore.domain.models.Comment;
import com.mdddetails.models.CommentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentMapper extends EntMapper<Comment, CommentEntity> {
    @Mapping(target = "article", ignore = true)
    Comment toDomain(CommentEntity entity);
}
