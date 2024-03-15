package com.openclassrooms.mddapi.mappers;

import com.openclassrooms.mddapi.dtos.UserDTO;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.services.UserService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public abstract class UserMapper implements EntityMapper<UserDTO, User> {

    @Autowired
    UserService userService;

    @Mappings({
            @Mapping(target = "subscriptions", expression = "java(userService.getByEmail(email).getSubscriptions())"),
    })
    public abstract UserDTO toDTO(User user, String email);
}
