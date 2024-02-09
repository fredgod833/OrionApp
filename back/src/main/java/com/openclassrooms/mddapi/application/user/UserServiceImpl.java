package com.openclassrooms.mddapi.application.user;

import com.openclassrooms.mddapi.application.user.dto.UserDto;
import com.openclassrooms.mddapi.domain.common.exceptions.InternalServerException;
import com.openclassrooms.mddapi.domain.common.exceptions.ResourceNotFoundException;
import com.openclassrooms.mddapi.domain.user.IUserRepository;
import com.openclassrooms.mddapi.domain.user.User;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;

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
            throw new ResourceNotFoundException("User not found with id : " + id);
        }
        return userMapper.toDto(user);
    }

    @Override
    public Boolean existById(Long id) {
        return userRepository.exist(id);
    }

    @Override
    public void delete(Long id) {
        try {
            userRepository.delete(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("User not found with id: " + id);
        } catch (DataAccessException e) {
            throw new InternalServerException("Error while trying to access DB to delete user");
        }
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
