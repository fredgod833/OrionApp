package com.openclassrooms.mddapi.infrastructure.controller;

import com.openclassrooms.mddapi.application.EntityMapper;
import com.openclassrooms.mddapi.application.user.IUserService;
import com.openclassrooms.mddapi.application.user.UserMapper;
import com.openclassrooms.mddapi.application.user.dto.UserDto;
import com.openclassrooms.mddapi.infrastructure.request.UserUpdateDto;
import com.openclassrooms.mddapi.infrastructure.request.UserUpdateMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private final IUserService userService;
    private final UserUpdateMapper userUpdateMapper;

    public UserController(IUserService userService, UserUpdateMapper userUpdateMapper) {
        this.userService = userService;
        this.userUpdateMapper = userUpdateMapper;
    }

    @GetMapping("/{id}")
    private ResponseEntity<?> getUser(@PathVariable("id")Long id) {
        return ResponseEntity.ok().body(userService.getById(id));
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<?> deleteUser(@PathVariable("id")Long id) {
        userService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    private ResponseEntity<?> updateUser(@PathVariable("id")Long id, UserUpdateDto userDto) {
        UserDto user = userUpdateMapper.toDto(userDto);
        userService.update(id, user);
        return ResponseEntity.ok().build();
    }
}
