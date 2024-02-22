package com.mddcore.usecases.iservice;

import com.mddcore.usecases.dto.UserDto;

public interface IUserService {
    void delete(Long id, String username);
    UserDto getById(Long id);
    void update(Long id, UserDto userDto);
}
