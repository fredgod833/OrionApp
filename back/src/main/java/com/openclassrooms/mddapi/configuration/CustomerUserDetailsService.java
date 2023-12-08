package com.openclassrooms.mddapi.configuration;

import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomerUserDetailsService implements UserDetailsService {
    private final UserService userService;

    public CustomerUserDetailsService(UserService userService){
        this.userService = userService;
    }

    //Return user
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        //Load user by email
        User user = userService.getByEmail(email);
        if (user == null){
            throw new UsernameNotFoundException("USER NOT FOUND");
        }
        return new CustomerUserDetails(user);
    }
}
