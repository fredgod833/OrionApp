package com.mdddetails.mapper;

import com.mddcore.domain.models.User;
import com.mdddetails.models.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapperDetails extends EntMapper<UserEntity, User> {
}
