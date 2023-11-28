package com.openclassrooms.mddapi.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.openclassrooms.mddapi.dto.CommentDto;
import com.openclassrooms.mddapi.models.Comment;

@Component
public class CommentMapper implements EntityMapper<CommentDto, Comment> {

    private final ModelMapper modelMapper;

    public CommentMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public Comment toEntity(CommentDto dto) {
        return modelMapper.map(dto, Comment.class);
    }

    @Override
    public CommentDto toDto(Comment entity) {
        return modelMapper.map(entity, CommentDto.class);
    }

    @Override
    public List<Comment> toEntity(List<CommentDto> dtoList) {
        return dtoList.stream()
                .map(dto -> modelMapper.map(dto, Comment.class))
                .collect(Collectors.toList());
    }
    @Override
    public List<CommentDto> toDto(List<Comment> entityList) {
        return entityList.stream()
                .map(entity -> modelMapper.map(entity, CommentDto.class))
                .collect(Collectors.toList());
    }
}
