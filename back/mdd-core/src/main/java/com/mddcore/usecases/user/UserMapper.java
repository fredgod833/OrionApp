package com.mddcore.usecases.user;

import com.mddcore.domain.models.User;
import com.mddcore.usecases.EntityMapper;
import com.mddcore.usecases.user.dto.UserDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper extends EntityMapper<User, UserDto> {
}
