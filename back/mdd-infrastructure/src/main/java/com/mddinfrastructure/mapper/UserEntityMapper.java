package com.mddinfrastructure.mapper;

import com.mddcore.domain.models.User;
import com.mddcore.usecases.mapper.EntityMapper;
import com.mddinfrastructure.request.UserEntityDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserEntityMapper extends EntityMapper<User, UserEntityDto> {
}
