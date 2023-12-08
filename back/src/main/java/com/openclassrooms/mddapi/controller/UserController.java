package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.model.Post;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.model.dto.UserDto;
import com.openclassrooms.mddapi.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
@RequestMapping("api/user")
public class UserController {
    private UserService userService;
    public UserController (UserService userService){
        this.userService = userService;
    }

    //Return user by its id
    @GetMapping("/{id_user}")
    public User getUserById(@PathVariable(name = "id_user")int id_user) {
        User user = userService.getUserById(id_user);
        return user;
    }

    //Subscribe a subject
    @PostMapping("/subscribe/{idUser}/{idSubject}")
    public ResponseEntity<User> subscribe(@PathVariable(name = "idUser") int id_user, @PathVariable(name = "idSubject") int id_subject){
            return ResponseEntity.ok(userService.subscribe(id_user, id_subject));
    }

    //Unsubscribe a subject
    @PutMapping("/unsubscribe/{id_user}/{id_subject}")
    public ResponseEntity<User> unsubscribe(@PathVariable(name = "id_user")int id_user, @PathVariable(name = "id_subject")int id_subject){
        return ResponseEntity.ok(userService.unsubscribe(id_user, id_subject));
    }
    //Delete user account
    @DeleteMapping("/delete/account/{id_user}")
    public User deleteUserAccount(@PathVariable(name = "id_user") int id_user){
        return userService.deleteUserAccount(id_user);
    }

    //Comment a post
    @PutMapping("/comments")
    public Post comments(@RequestBody Post post){
        return userService.commentPost(post);
    }

    //Change its user username and email
    @PutMapping("/change-user/username-email")
    public User changeUsernameAndEmail(@RequestBody UserDto userDto){
        return userService.changeUserUsernameAndEmail(userDto);
    }
}
