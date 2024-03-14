package com.openclassrooms.mddapi.mappers;

import com.openclassrooms.mddapi.dtos.UserDTO;
import com.openclassrooms.mddapi.models.Post;
import com.openclassrooms.mddapi.models.Theme;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.services.UserService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring", uses = {UserService.class}, imports = {Theme.class, Post.class})
public abstract class UserMapper implements EntityMapper<UserDTO, User> {

    @Autowired
    UserService userService;

    @Mappings({
            @Mapping(target = "subscriptions", expression = "java(userService.getByEmail(email).getSubscriptions())")
    })
    public abstract UserDTO toDTO(User user, String email);

    @Mappings({})
    public abstract User toEntity(UserDTO userDTO, String email);
}
