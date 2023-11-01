package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.dto.UserDto;
import com.openclassrooms.mddapi.services.UserService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/api/me")
    public UserDto getCurrentUser() {
        return userService.findUserById(1L);
    }

    @GetMapping("/api/users/{id}")
    public UserDto getUserById(@PathVariable Long id) {
        return userService.findUserById(id);
    }

    @GetMapping("/api/users")
    public List<UserDto> getAllUsers() {
        return userService.findAllUsers();
    }
}
