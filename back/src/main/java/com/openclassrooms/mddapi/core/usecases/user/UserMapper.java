package com.openclassrooms.mddapi.core.usecases.user;

import com.openclassrooms.mddapi.core.usecases.EntityMapper;
import com.openclassrooms.mddapi.core.usecases.user.dto.UserDto;
import com.openclassrooms.mddapi.core.domain.models.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper extends EntityMapper<User, UserDto> {
}
