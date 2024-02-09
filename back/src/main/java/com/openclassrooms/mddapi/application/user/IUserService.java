package com.openclassrooms.mddapi.application.user;

import com.openclassrooms.mddapi.application.user.dto.UserDto;

public interface IUserService {
    Boolean existById(Long id);
    void delete(Long id);
    UserDto getById(Long id);
    void update(Long id, UserDto userDto);
}
