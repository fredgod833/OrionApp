package com.openclassrooms.mddapi.details.mapper;

import com.tonight.back.details.models.UserEntity;
import com.tonight.back.domain.user.User;
import com.tonight.back.useCases.commom.EntityMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapperDetails extends EntityMapper<UserEntity, User> {
}
