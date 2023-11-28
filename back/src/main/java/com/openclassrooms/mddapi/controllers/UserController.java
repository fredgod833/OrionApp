package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.dto.UserDto;
import com.openclassrooms.mddapi.mapper.UserMapper;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.services.UserService;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserMapper userMapper;
    private final UserService userService;


    public UserController(UserService userService, UserMapper userMapper) {
        this.userMapper = userMapper;
        this.userService = userService;
    }

    // Retrouver un utilisateur par son addresse mail
    @GetMapping("")
    public ResponseEntity<UserDto> findByEmail(Authentication authentication) {
        try {
            User user = this.userService.findByEmail(authentication.getName());

            if (user == null) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok().body(this.userMapper.toDto(user));
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Modifier le nom de l'utilisateur et l'adresse mail
    @PutMapping("")
    public ResponseEntity<UserDto> update(@RequestBody User user, Authentication authentication) {
        try {

            User userToUpdate = this.userService.findByEmail(authentication.getName());

            if (userToUpdate == null) {
                return ResponseEntity.notFound().build();
            }

            userToUpdate.setName(user.getName());
            userToUpdate.setEmail(user.getEmail());

            this.userService.create(userToUpdate);
            return ResponseEntity.ok().body(this.userMapper.toDto(userToUpdate));
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Supprimer l'utilisateur
    @DeleteMapping("")
    public ResponseEntity<?> delete(Authentication authentication) {
        try {
            User user = this.userService.findByEmail(authentication.getName());

            if (user == null) {
                return ResponseEntity.notFound().build();
            }

            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            if (!Objects.equals(userDetails.getUsername(), user.getEmail())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            this.userService.delete(user.getId());
            return ResponseEntity.ok().build();
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // S'abonner à un Topic
    @GetMapping("/subscribe/{topicId}")
    public ResponseEntity<?> subscribe(@PathVariable("topicId") String topicId, Authentication authentication) {
        try {
            User user = this.userService.findByEmail(authentication.getName());

            if (user == null) {
                return ResponseEntity.notFound().build();
            }

            this.userService.subscribeToTopic(user, Long.parseLong(topicId));
            return ResponseEntity.ok().build();
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Se désabonner d'un Topic
    @GetMapping("/unsubscribe/{topicId}")
    public ResponseEntity<?> unsubscribe(@PathVariable("topicId") String topicId, Authentication authentication) {
        try {
            User user = this.userService.findByEmail(authentication.getName());

            if (user == null) {
                return ResponseEntity.notFound().build();
            }

            this.userService.unsubscribeToTopic(user, Long.parseLong(topicId));
            return ResponseEntity.ok().build();
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().build();
        }
    }

}
