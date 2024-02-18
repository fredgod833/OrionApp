package com.mdddetails.mapper;


import com.mddcore.usecases.user.dto.UserDto;
import com.mdddetails.models.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapperDetails extends EntMapper<UserEntity, UserDto> {
}
