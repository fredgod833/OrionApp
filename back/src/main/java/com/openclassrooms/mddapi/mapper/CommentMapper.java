package com.openclassrooms.mddapi.mapper;

import com.openclassrooms.mddapi.models.dto.CommentDto;
import com.openclassrooms.mddapi.models.dto.UserDto;
import com.openclassrooms.mddapi.models.entities.CommentEntity;
import com.openclassrooms.mddapi.models.entities.UserEntity;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface CommentMapper extends EntityMapper<CommentDto, CommentEntity> {

    UserDto userEntityToUserDto (UserEntity entity);

    UserEntity userDtoToUserEntity(UserDto dto);

}
