package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.model.Comments;
import com.openclassrooms.mddapi.model.Post;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.model.dto.UserDto;
import com.openclassrooms.mddapi.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * User api router
 */
@RestController
@RequestMapping("api/user")
public class UserController {
    private UserService userService;
    public UserController (UserService userService){
        this.userService = userService;
    }

    /**
     * Get user by its id
     * @param id_user entry
     * @return user
     */
    @GetMapping("/{id_user}")
    public User getUserById(@PathVariable(name = "id_user")int id_user) {
        User user = userService.getUserById(id_user);
        return user;
    }

    /**
     * Subscribe a subject
     * @param id_user entry
     * @param id_subject entry
     * @return user with its subscription
     */
    @PostMapping("/subscribe/{idUser}/{idSubject}")
    public ResponseEntity<User> subscribe(@PathVariable(name = "idUser") int id_user, @PathVariable(name = "idSubject") int id_subject){
            return ResponseEntity.ok(userService.subscribe(id_user, id_subject));
    }

    /**
     * Unsubscribe a subject
     * @param id_user entry
     * @param id_subject entry
     * @return user with its subscription updated
     */
    @PutMapping("/unsubscribe/{id_user}/{id_subject}")
    public ResponseEntity<User> unsubscribe(@PathVariable(name = "id_user")int id_user, @PathVariable(name = "id_subject")int id_subject){
        return ResponseEntity.ok(userService.unsubscribe(id_user, id_subject));
    }

    /**
     * Delete user account
     * @param id_user entry
     * @return Deleted account
     */
    @DeleteMapping("/delete/account/{id_user}")
    public User deleteUserAccount(@PathVariable(name = "id_user") int id_user){
        return userService.deleteUserAccount(id_user);
    }

    /**
     * Comment a post
     * @param post entry
     * @return post
     */
    @PutMapping("/comments")
    public Post comments(@RequestBody Post post, @RequestBody Comments comment){
        return userService.commentPost(post, comment);
    }

    /**
     * Change its user username and email
     * @param userDto entry
     * @return user updated
     */
    @PutMapping("/change-user/username-email")
    public User changeUsernameAndEmail(@RequestBody UserDto userDto){
        return userService.changeUserUsernameAndEmail(userDto);
    }

    //TODO: This is a test
    @PostMapping("/comment/{post_id}")
    public ResponseEntity<Comments> newComment(@RequestBody Comments comments, @PathVariable(name = "post_id") int id_post){
        return ResponseEntity.ok(userService.newComments(comments, id_post));
    }
    @GetMapping("/comment")
    public List<Comments> newComment(){
        return userService.getCommentsList();
    }

}
