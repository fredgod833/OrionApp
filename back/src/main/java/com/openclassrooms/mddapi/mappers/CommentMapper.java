package com.openclassrooms.mddapi.mappers;

import com.openclassrooms.mddapi.dtos.CommentDTO;
import com.openclassrooms.mddapi.models.Comment;
import com.openclassrooms.mddapi.services.PostService;
import com.openclassrooms.mddapi.services.UserService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public abstract class CommentMapper {

    @Autowired
    UserService userService;

    @Autowired
    PostService postService;

    @Mappings({
            @Mapping(target = "author", source = "author.username"),
    })
    public abstract CommentDTO toDTO(Comment comment);

    @Mappings({
            @Mapping(target = "author", expression = "java(userService.getByEmail(email))"),
            @Mapping(target = "post", expression = "java(postService.get(postId))")
    })
    public abstract Comment toEntity(Integer postId, CommentDTO commentDTO, String email);
}
