package com.openclassrooms.mddapi.mapper;

import com.openclassrooms.mddapi.models.dto.PostDto;
import com.openclassrooms.mddapi.models.dto.TopicDto;
import com.openclassrooms.mddapi.models.dto.UserDto;
import com.openclassrooms.mddapi.models.entities.PostEntity;
import com.openclassrooms.mddapi.models.entities.TopicEntity;
import com.openclassrooms.mddapi.models.entities.UserEntity;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface PostMapper extends EntityMapper<PostDto, PostEntity> {

    TopicDto topicEntityToTopicDto (TopicEntity entity);

    TopicEntity topicDtoToTopicEntity(TopicDto dto);

    UserDto userEntityToUserDto (UserEntity entity);

    UserEntity userDtoToUserEntity(UserDto dto);

}
