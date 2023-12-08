package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.model.Post;
import com.openclassrooms.mddapi.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//Post api controller
@RestController
@RequestMapping(path = "api/post")
public class PostController {
    private final PostService postService;
    public PostController(PostService postService){
        this.postService = postService;
    }

    //Return list of post
    @GetMapping("/post_list")
    public List<Post>getPostList(){
        return postService.postList();
    }

    //Return post by its id
    @GetMapping("/{post_id}")
    public ResponseEntity<?> findPostById(@PathVariable(name = "post_id") int post_id){
        return postService.findPostById(post_id);
    }
    //Create a post
    @PostMapping("/create_post/{id_subject}")
    public Post createPost(@RequestBody Post post, @PathVariable(name = "id_subject")int id_subject){
        return postService.createPost(post, id_subject);
    }

}
