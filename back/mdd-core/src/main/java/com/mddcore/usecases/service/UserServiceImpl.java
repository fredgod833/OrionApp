package com.mddcore.usecases.service;

import com.mddcore.domain.models.User;
import com.mddcore.domain.repository.IUserRepository;
import com.mddcore.usecases.dto.UserDto;
import com.mddcore.usecases.iservice.IUserService;
import com.mddcore.usecases.mapper.UserMapper;

public class UserServiceImpl implements IUserService {
    private final IUserRepository userRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(IUserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserDto getById(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            throw new IllegalArgumentException("User not found with id : " + id);
        }
        return userMapper.toDto(user);
    }

    @Override
    public void delete(Long id, String authenticatedUsername) {
        User user = userRepository.findById(id).orElse(null);

        if(user == null) {
            throw new IllegalArgumentException("User not found, cant delete it");
        }

        if(!user.getUsername().equals(authenticatedUsername)) {
            throw new IllegalStateException("You cant delete other user except you");
        }
        userRepository.delete(user);
    }

    @Override
    public void update(Long id, UserDto userDto) {
        userRepository.findById(id)
                .map(user -> {
                    user.setEmail(userDto.getEmail());
                    user.setPassword(userDto.getPassword());
                    user.setUsername(userDto.getUsername());
                    return userRepository.save(user);
                })
                .orElseThrow(() -> new IllegalArgumentException("User to update not found"));
    }

}
