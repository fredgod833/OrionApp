package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.model.Post;
import com.openclassrooms.mddapi.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * Post api controller
 */
@RestController
@RequestMapping(path = "api/post")
public class PostController {
    private final PostService postService;
    public PostController(PostService postService){
        this.postService = postService;
    }

    /**
     * Get a list of post
     * @return list
     */
    @GetMapping("/post_list")
    public List<Post>getPostList(){
        return postService.postList();
    }

    /**
     * Get post by its id
     * @param post_id entry
     * @return post
     */
    @GetMapping("/{post_id}")
    public ResponseEntity<?> findPostById(@PathVariable(name = "post_id") int post_id){
        return postService.findPostById(post_id);
    }

    /**
     * Create a post
     * @param post information to create
     * @param id_subject for post concerns
     * @return new post
     */
    @PostMapping("/create_post/{id_subject}")
    public Post createPost(@RequestBody Post post, @PathVariable(name = "id_subject")int id_subject){
        return postService.createPost(post, id_subject);
    }

}
