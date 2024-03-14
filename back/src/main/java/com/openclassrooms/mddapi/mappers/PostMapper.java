package com.openclassrooms.mddapi.mappers;

import com.openclassrooms.mddapi.dtos.PostDTO;
import com.openclassrooms.mddapi.models.Post;
import com.openclassrooms.mddapi.models.Theme;
import com.openclassrooms.mddapi.services.ThemeService;
import com.openclassrooms.mddapi.services.UserService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring", uses = {UserService.class, ThemeService.class}, imports = {Theme.class, Post.class})
public abstract class PostMapper implements EntityMapper<PostDTO, Post> {

    @Autowired
    UserService userService;

    @Autowired
    ThemeService themeService;

    @Mappings({
            @Mapping(target = "author", source = "author.username"),
            @Mapping(target = "theme", source = "theme.name"),
    })
    public abstract PostDTO toDTO(Post post);

    @Mappings({
            @Mapping(target = "author", expression = "java(userService.getByEmail(email))"),
            @Mapping(target = "theme", expression = "java(themeService.getByName(post.getTheme()))"),
    })
    public abstract Post toEntity(PostDTO post, String email);
}
