package com.openclassrooms.mddapi.mapper;

import com.openclassrooms.mddapi.models.dto.TopicDto;
import com.openclassrooms.mddapi.models.entities.TopicEntity;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface TopicMapper extends EntityMapper<TopicDto, TopicEntity> {

}
