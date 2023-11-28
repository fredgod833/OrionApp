package com.openclassrooms.mddapi.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.openclassrooms.mddapi.dto.TopicDto;
import com.openclassrooms.mddapi.models.Topic;

@Component
public class TopicMapper implements EntityMapper<TopicDto, Topic> {

  private final ModelMapper modelMapper;

  public TopicMapper(ModelMapper modelMapper) {
    this.modelMapper = modelMapper;
  }

  @Override
  public Topic toEntity(TopicDto dto) {
    return modelMapper.map(dto, Topic.class);
  }

  @Override
  public TopicDto toDto(Topic entity) {
    return modelMapper.map(entity, TopicDto.class);
  }

  @Override
  public List<Topic> toEntity(List<TopicDto> dtoList) {
    return dtoList.stream()
      .map(dto -> modelMapper.map(dto, Topic.class))
      .collect(Collectors.toList());
  }

  @Override
  public List<TopicDto> toDto(List<Topic> entityList) {
    return entityList.stream()
      .map(entity -> modelMapper.map(entity, TopicDto.class))
      .collect(Collectors.toList());
  }
}
