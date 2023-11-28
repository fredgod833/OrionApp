package com.openclassrooms.mddapi.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.openclassrooms.mddapi.dto.PostDto;
import com.openclassrooms.mddapi.models.Post;

@Component
public class PostMapper implements EntityMapper<PostDto, Post> {

  private final ModelMapper modelMapper;

  public PostMapper(ModelMapper modelMapper) {
    this.modelMapper = modelMapper;
  }

  @Override
  public Post toEntity(PostDto dto) {
    return modelMapper.map(dto, Post.class);
  }

  @Override
  public PostDto toDto(Post entity) {
    return modelMapper.map(entity, PostDto.class);
  }

  @Override
  public List<Post> toEntity(List<PostDto> dtoList) {
    return dtoList.stream()
    .map(dto -> modelMapper.map(dto, Post.class))
    .collect(Collectors.toList());
  }

  @Override
  public List<PostDto> toDto(List<Post> entityList) {
    return entityList.stream()
      .map(entity -> modelMapper.map(entity, PostDto.class))
      .collect(Collectors.toList());
  }
}
