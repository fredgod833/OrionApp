package com.mddinfrastructure.mapper;

import com.mddcore.domain.models.User;
import com.mddinfrastructure.response.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserPresenterMapper extends EntityMapper<User, UserResponse> {
    UserPresenterMapper INSTANCE = Mappers.getMapper(UserPresenterMapper.class);

}
