package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.dto.PostDto;
import com.openclassrooms.mddapi.mapper.PostMapper;
import com.openclassrooms.mddapi.models.Post;
import com.openclassrooms.mddapi.models.Topic;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.services.PostService;
import com.openclassrooms.mddapi.services.TopicService;
import com.openclassrooms.mddapi.services.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/posts")
public class PostController {

    private static final Logger logger = LoggerFactory.getLogger(PostController.class);
    private PostService postService;
    private UserService userService;
    private TopicService topicService;
    private PostMapper postMapper;

    public PostController(PostService postService, PostMapper postMapper, UserService userService, TopicService topicService) {
      this.postService = postService;
      this.postMapper = postMapper;
      this.userService = userService;
      this.topicService = topicService;
    }

    // Tout les articles de la db
    @GetMapping("")
    public ResponseEntity<List<PostDto>> findAll() {
      List<Post> posts = this.postService.findAll();

      return ResponseEntity.ok().body(this.postMapper.toDto(posts));
    }

    // Un article par son id dans la db
    @GetMapping("/{id}")
    public ResponseEntity<PostDto> findById(@PathVariable("id") String id) {
        try {
          Post post = this.postService.findById(Long.valueOf(id));

          if (post == null) {
            return ResponseEntity.notFound().build();
          }

          return ResponseEntity.ok().body(this.postMapper.toDto(post));
        } catch (NumberFormatException e) {
          return ResponseEntity.badRequest().build();
        }
    }

    // Créer un article en db
    @PostMapping
    public ResponseEntity<PostDto> createPost(@RequestBody Post post, Authentication authentication) {
      try {
        String currentUserId = authentication.getName();

        logger.info("currentUserId: "+ currentUserId);

        User currentUser = this.userService.findByEmail(currentUserId);
        Topic currentTopic = this.topicService.findById(post.getTopic().getId());

        Post newPost = new Post();
        newPost.setTitle(post.getTitle());
        newPost.setContent(post.getContent());
        newPost.setTopic(currentTopic);
        newPost.setUser(currentUser);

        Post createdPost = this.postService.create(newPost);

        return ResponseEntity.ok().body(this.postMapper.toDto(createdPost));
      } catch (Exception e) {
        logger.error("Il y à eu une erreur pendant la création d'un article: " + e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
      }

    }
}
