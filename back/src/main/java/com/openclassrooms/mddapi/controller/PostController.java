package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.model.Post;
import com.openclassrooms.mddapi.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/post")
public class PostController {
    private final PostService postService;
    public PostController(PostService postService){
        this.postService = postService;
    }
    @GetMapping("/post_list")
    public List<Post>getPostList(){
        return postService.postList();
    }

    @GetMapping("/{post_id}")
    public ResponseEntity<?> findPostById(@PathVariable(name = "post_id") int post_id){
        return postService.findPostById(post_id);
    }

    @PostMapping("/create_post")
    public String createPost(@RequestBody Post post){
        return postService.createPost(post);
    }

}
