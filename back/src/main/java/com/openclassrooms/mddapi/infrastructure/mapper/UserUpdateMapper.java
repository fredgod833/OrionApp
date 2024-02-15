package com.openclassrooms.mddapi.infrastructure.mapper;

import com.openclassrooms.mddapi.core.usecases.EntityMapper;
import com.openclassrooms.mddapi.core.usecases.user.dto.UserDto;
import com.openclassrooms.mddapi.infrastructure.request.UserUpdateDto;

public interface UserUpdateMapper extends EntityMapper<UserUpdateDto, UserDto> {
}
