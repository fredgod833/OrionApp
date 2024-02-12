package com.openclassrooms.mddapi.application.user;

import com.openclassrooms.mddapi.application.user.auth.IUserDetails;
import com.openclassrooms.mddapi.application.user.dto.UserDto;
import com.openclassrooms.mddapi.domain.common.exceptions.ResourceNotFoundException;
import com.openclassrooms.mddapi.domain.common.exceptions.UnauthorizedException;
import com.openclassrooms.mddapi.domain.user.IUserRepository;
import com.openclassrooms.mddapi.domain.user.User;


import java.util.Objects;
import java.util.Optional;

public class UserServiceImpl implements IUserService {
    private final IUserRepository userRepository;
    private final UserMapper userMapper;
    private final IUserDetails userDetails;

    public UserServiceImpl(IUserRepository userRepository, UserMapper userMapper, IUserDetails userDetails) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.userDetails = userDetails;
    }

    @Override
    public UserDto getById(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            throw new ResourceNotFoundException("User not found with id : " + id);
        }
        return userMapper.toDto(user);
    }

    @Override
    public void delete(Long id) {
        Optional<User> user = userRepository.findById(id);

        if(user.isEmpty()) {
            throw new ResourceNotFoundException("User not found, cant delete it");
        }

        if(!Objects.equals(userDetails.getUsername(), user.get().getUsername())) {
            throw new UnauthorizedException("You cant delete other user except you");
        }
        userRepository.delete(id);
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
                .orElseThrow(() -> new ResourceNotFoundException("User to update not found"));
    }

}
