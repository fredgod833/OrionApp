package com.openclassrooms.p6.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.openclassrooms.p6.model.Comments;
import com.openclassrooms.p6.payload.response.CommentResponse;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    @Mapping(source = "user.username", target = "username")
    CommentResponse toDtoComment(Comments comment);

    Iterable<CommentResponse> toDtoComments(List<Comments> comments);
}