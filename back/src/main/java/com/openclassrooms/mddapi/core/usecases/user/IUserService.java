package com.openclassrooms.mddapi.core.usecases.user;

import com.openclassrooms.mddapi.core.usecases.user.dto.UserDto;

public interface IUserService {
    void delete(Long id);
    UserDto getById(Long id);
    void update(Long id, UserDto userDto);
}
