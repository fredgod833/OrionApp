package com.openclassrooms.mddapi.security;

import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserService userService;

    @Autowired
    public UserDetailsServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        User user = usernameOrEmail.contains("@") ?
                userService.getByEmail(usernameOrEmail) : userService.getByUsername(usernameOrEmail);

        return UserDetailsImpl
                .builder()
                .id(user.getId())
                .username(user.getEmail())
                .password(user.getPassword())
                .name(user.getUsername())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
}
