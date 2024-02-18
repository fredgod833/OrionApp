package com.mddcore.usecases.user;

import com.mddcore.usecases.user.dto.UserDto;

public interface IUserService {
    void delete(Long id, String username);
    UserDto getById(Long id);
    void update(Long id, UserDto userDto);
}
