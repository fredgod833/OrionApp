package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.exceptions.InvalidUserException;
import com.openclassrooms.mddapi.models.dto.UserDto;
import com.openclassrooms.mddapi.models.payload.request.UserRequest;
import com.openclassrooms.mddapi.security.services.UserDetailsImpl;
import com.openclassrooms.mddapi.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> findById(@PathVariable("id") String id) throws InvalidUserException {
        try {
            UserDto user = this.userService.findById(Integer.valueOf(id));
            return ResponseEntity.ok().body(user);
        } catch (NumberFormatException e) {
            throw new InvalidUserException("Identifiant utilisateur invalide");
        }
    }

}
