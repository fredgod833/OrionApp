package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/user")
public class UserController {
    private UserService userService;
    public UserController (UserService userService){
        this.userService = userService;
    }
    @GetMapping("/{id_user}")
    public ResponseEntity<?> getUserById(@PathVariable(name = "id_user")int id_user){
        return userService.user_id(id_user);
    }
}
