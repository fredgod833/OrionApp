package com.openclassrooms.mddapi.application.user.auth;

import com.openclassrooms.mddapi.application.user.dto.UserDto;

import javax.servlet.http.HttpServletRequest;

public interface IAuthService {

    String login(HttpServletRequest request);
    String register(UserDto user);
}
