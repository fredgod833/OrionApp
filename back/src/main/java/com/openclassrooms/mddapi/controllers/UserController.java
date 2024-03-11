package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.dtos.UpdatedUserDTO;
import com.openclassrooms.mddapi.dtos.UserDTO;
import com.openclassrooms.mddapi.exceptions.UserNotFoundException;
import com.openclassrooms.mddapi.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public ResponseEntity<UserDTO> getUser(Principal principal) {
        try {
            UserDTO userDTO = userService.getUserByEmail(principal.getName());
            return ResponseEntity.ok(userDTO);
        } catch (UserNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<UpdatedUserDTO> updateUser(@PathVariable("id") int id, @RequestBody UpdatedUserDTO updatedUser) {
        return ResponseEntity.ok(userService.updateUser(id, updatedUser));
    }

    @PostMapping("/follow/{id}")
    public ResponseEntity<UserDTO> subscribe(@PathVariable("id") int id, @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(userService.subscribeToTheme(id, userDetails.getUsername()));
    }

    @DeleteMapping("/unfollow/{id}")
    public ResponseEntity<UserDTO> unSubscribe(@PathVariable("id") int id, @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(userService.unsubscribeFromTheme(id, userDetails.getUsername()));
    }
}
