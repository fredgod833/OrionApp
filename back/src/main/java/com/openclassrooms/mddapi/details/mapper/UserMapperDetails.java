package com.openclassrooms.mddapi.details.mapper;

import com.openclassrooms.mddapi.application.EntityMapper;
import com.openclassrooms.mddapi.details.models.UserEntity;
import com.openclassrooms.mddapi.domain.user.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapperDetails extends EntityMapper<UserEntity, User> {
}
