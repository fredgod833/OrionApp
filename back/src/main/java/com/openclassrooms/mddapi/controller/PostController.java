package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.dto.ResponseDto;
import com.openclassrooms.mddapi.dto.PostDto;
import com.openclassrooms.mddapi.model.Post;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.service.PostService;
import com.openclassrooms.mddapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    @Autowired
    UserService userService;

    @Autowired
    PostService postService;

    private final ResponseDto responseDto = new ResponseDto();

    @PostMapping(
            value ="",
            consumes = { MediaType.MULTIPART_FORM_DATA_VALUE },
            produces = MediaType.APPLICATION_JSON_VALUE
        )
    public ResponseDto createPost(
            @ModelAttribute PostDto postDto,
            Authentication authentication
        ) {
        User user = userService.getUserByEmail(authentication.getName());
        postService.savePost(postDto, user);
        responseDto.setResponse("Post created !");
        return responseDto;
    }

    @PutMapping(
            value ="",
            consumes = { MediaType.MULTIPART_FORM_DATA_VALUE },
            produces = MediaType.APPLICATION_JSON_VALUE
        )
    public ResponseDto updatePost(@ModelAttribute PostDto postDto) {
        postService.updatePost(postDto);
        responseDto.setResponse("Post updated !");
        return responseDto;
    }


    @GetMapping(value = "/{id}", produces = { "application/json" })
    public ResponseEntity<Post> getPostById(@PathVariable Integer id) {
        try {
            Post post = postService.getPost(id);
            return new ResponseEntity<Post>(post, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<Post>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = "/{id}", produces = { "application/json" })
    public ResponseDto deletePost(@PathVariable Integer id, Authentication authentication){
        User user = userService.getUserByEmail(authentication.getName());
        String result = postService.deletePost(id, user);
        responseDto.setResponse(result);
        return responseDto;
    }

    @GetMapping(value = "/subscription", produces = { "application/json" })
    public ResponseEntity<List<Post>> getAllPostSubscription(@RequestParam List<String> values){
        List<Post> posts = this.postService.getAllPosts(values);
        return ResponseEntity.ok().body(posts);
    }
}
