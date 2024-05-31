package com.openclassrooms.mddapi.mapper;

import com.openclassrooms.mddapi.models.dto.CommentDto;
import com.openclassrooms.mddapi.models.entities.CommentEntity;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface CommentMapper extends EntityMapper<CommentDto, CommentEntity> {

}
