package com.openclassrooms.mddapi.mapper;

import com.openclassrooms.mddapi.dto.UserDto;
import com.openclassrooms.mddapi.models.User;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.w3c.dom.Entity;

@Component
public class UserMapper implements EntityMapper<UserDto, User> {

    private final ModelMapper modelMapper;

    public UserMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public User toEntity(UserDto dto) {
        return modelMapper.map(dto, User.class);
    }

    @Override
    public UserDto toDto(User entity) {
        return modelMapper.map(entity, UserDto.class);
    }

    @Override
    public List<User> toEntity(List<UserDto> dtoList) {
        return dtoList.stream()
                .map(dto -> modelMapper.map(dto, User.class))
                .collect(Collectors.toList());
    }
    @Override
    public List<UserDto> toDto(List<User> entityList) {
        return entityList.stream()
                .map(entity -> modelMapper.map(entity, UserDto.class))
                .collect(Collectors.toList());
    }
}
