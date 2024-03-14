package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.dtos.UserDTO;
import com.openclassrooms.mddapi.exceptions.UserNotFoundException;
import com.openclassrooms.mddapi.mappers.UserMapper;
import com.openclassrooms.mddapi.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    private final ModelMapper modelMapper;
    private final UserMapper userMapper;

    public UserController(UserService userService, ModelMapper modelMapper, UserMapper userMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.userMapper = userMapper;
    }

    @GetMapping()
    public ResponseEntity<UserDTO> getUser(Principal principal) {
        try {
            return ResponseEntity.ok(
                    modelMapper.map(
                            userService.getByEmail(principal.getName()), UserDTO.class));
        } catch (UserNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<UserDTO> updateUser(
            @PathVariable("id") int id,
            @RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(
                modelMapper.map(
                        userService.updateUser(id, userDTO), UserDTO.class));
    }

    // InvalidDefinitionException
    @PostMapping("/follow/{id}")
    public ResponseEntity<UserDTO> subscribe(
            @PathVariable("id") int id,
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(
          userMapper.toDTO(
                  userService.subscribeToTheme(id, userDetails.getUsername()), userDetails.getUsername()));
//        return ResponseEntity.ok(
//                modelMapper.map(
//                        userService.subscribeToTheme(id, userDetails.getUsername()), UserDTO.class));
    }

    @DeleteMapping("/unfollow/{id}")
    public ResponseEntity<UserDTO> unSubscribe(
            @PathVariable("id") int id,
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(
                modelMapper.map(
                        userService.unsubscribeFromTheme(id, userDetails.getUsername()), UserDTO.class));
    }
}
