package com.openclassrooms.mddapi.core.usecases.user;

import com.openclassrooms.mddapi.core.usecases.user.auth.securityAuth.IUserDetails;
import com.openclassrooms.mddapi.core.usecases.user.dto.UserDto;
import com.openclassrooms.mddapi.common.exceptions.ResourceNotFoundException;
import com.openclassrooms.mddapi.common.exceptions.UnauthorizedException;
import com.openclassrooms.mddapi.core.domain.repository.IUserRepository;
import com.openclassrooms.mddapi.core.domain.models.User;

import java.util.Objects;

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
        User user = userRepository.findById(id).orElse(null);

        if(user == null) {
            throw new ResourceNotFoundException("User not found, cant delete it");
        }

        if(!Objects.equals(userDetails.getUsername(), user.getUsername())) {
            throw new UnauthorizedException("You cant delete other user except you");
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
                .orElseThrow(() -> new ResourceNotFoundException("User to update not found"));
    }

}
