package com.mddinfrastructure.mapper;

import com.mddcore.domain.models.User;
import com.mddcore.usecases.mapper.EntityMapper;
import com.mddinfrastructure.response.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper extends EntityMapper<User, UserResponse> {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

}
