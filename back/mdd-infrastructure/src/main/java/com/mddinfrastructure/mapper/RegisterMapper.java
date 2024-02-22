package com.mddinfrastructure.mapper;

import com.mddcore.usecases.dto.UserDto;
import com.mddcore.usecases.mapper.EntityMapper;
import com.mddinfrastructure.request.RegisterRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RegisterMapper extends EntityMapper<RegisterRequest, UserDto> {
}
