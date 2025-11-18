package com.openclassrooms.mddapi.mapper;

import com.openclassrooms.mddapi.models.dto.UserDto;
import com.openclassrooms.mddapi.models.entities.UserEntity;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

/**
 * Mapper Mapstruct Entity <-> DTO des objets Utilisateur
 */
@Component
@Mapper(componentModel = "spring")
public interface UserMapper extends EntityMapper<UserDto, UserEntity> {

}
