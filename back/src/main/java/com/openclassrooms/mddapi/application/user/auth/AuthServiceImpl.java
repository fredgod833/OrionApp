package com.openclassrooms.mddapi.application.user.auth;

import com.openclassrooms.mddapi.application.user.UserMapper;
import com.openclassrooms.mddapi.application.user.dto.UserDto;
import com.openclassrooms.mddapi.domain.common.exceptions.UnauthorizedException;
import com.openclassrooms.mddapi.domain.user.IUserRepository;
import com.openclassrooms.mddapi.domain.user.User;
import com.openclassrooms.mddapi.domain.user.auth.ITokenProvider;
import com.openclassrooms.mddapi.domain.user.auth.IUserDetails;
import com.openclassrooms.mddapi.domain.user.auth.IUserDetailsService;

import javax.servlet.http.HttpServletRequest;


public class AuthServiceImpl implements IAuthService{
    private final ITokenProvider tokenProvider;
    private final IUserRepository userRepository;
    private final IUserDetailsService<IUserDetails> userDetailsService;
    private final UserMapper userMapper;

    public AuthServiceImpl(ITokenProvider tokenProvider, IUserRepository userRepository, IUserDetailsService<IUserDetails> userDetailsService, UserMapper userMapper) {
        this.tokenProvider = tokenProvider;
        this.userRepository = userRepository;
        this.userDetailsService = userDetailsService;
        this.userMapper = userMapper;
    }

    @Override
    public String login(HttpServletRequest request) {
        try {
            String token = tokenProvider.extractToken(request);
            if(token != null && tokenProvider.validateToken(token)) {
                String username = tokenProvider.getUsernameFromToken(token);
                IUserDetails userDetails = userDetailsService.loadUserByUsername(username);
                if(userDetails != null && userDetails.isEnabled()) {
                    return tokenProvider.createToken(username, userDetails.getAuthorities());
                }
            }
        } catch (Exception e) {
            throw  new UnauthorizedException("Problem with login request");
        }
        return null;
    }

    @Override
    public String register(UserDto userDto) {
        User user = userMapper.toEntity(userDto);
        userRepository.save(user);
        return tokenProvider.createToken(user.getUsername(), null);
    }
}
