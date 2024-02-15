package com.openclassrooms.mddapi.core.usecases.user.auth;

import com.openclassrooms.mddapi.common.request.LoginRequest;
import com.openclassrooms.mddapi.core.usecases.user.dto.UserDto;

public interface IAuthService {

    String login(LoginRequest request);
    String register(UserDto user);
}
