package com.mddcore.usecases.mapper;

import com.mddcore.domain.models.User;
import com.mddcore.usecases.dto.UserDto;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper extends EntityMapper<User, UserDto> {
}
