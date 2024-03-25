package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.dtos.PostDTO;
import com.openclassrooms.mddapi.exceptions.ResourceNotFoundException;
import com.openclassrooms.mddapi.exceptions.UserNotFoundException;
import com.openclassrooms.mddapi.mappers.PostMapper;
import com.openclassrooms.mddapi.models.Post;
import com.openclassrooms.mddapi.services.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.security.Principal;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class PostControllerTests {

    private PostController controller;
    private PostService postService;
    private PostMapper postMapper;

    Principal principal = () -> "username";

    @BeforeEach
    public void setUp() {
        postService = mock(PostService.class);
        postMapper = mock(PostMapper.class);
        controller = new PostController(postService, postMapper);
    }

    @Test
    public void testGetPosts_Success() {
        Principal principal = () -> "username";
        Post post = new Post();
        post.setTitle("Post 1");
        post.setContent("Post 1 content");
        post.setId(1);
        Post post2 = new Post();
        post2.setTitle("Post 2");
        post2.setContent("Post 2 content");
        post.setId(2);
        List<Post> posts = List.of(post, post2);
        PostDTO postDTO = new PostDTO();
        postDTO.setId(1);
        postDTO.setTitle("PostDTO 1");
        postDTO.setContent("PostDTO 1 content");
        PostDTO postDTO2 = new PostDTO();
        postDTO2.setId(2);
        postDTO2.setTitle("PostDTO 2");
        postDTO2.setContent("PostDTO 2 content");
        List<PostDTO> postDTOs = List.of(postDTO, postDTO2);

        when(postService.getAll(principal.getName())).thenReturn(posts);
        when(postMapper.toDTO(posts)).thenReturn(postDTOs);

        ResponseEntity<List<PostDTO>> response = controller.getPosts(principal);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(posts.size(), Objects.requireNonNull(response.getBody()).size());
    }

    @Test
    public void testGetPosts_UserNotFound() {
        when(postService.getAll(anyString())).thenThrow(new UserNotFoundException("User not found"));

        ResponseEntity<List<PostDTO>> response = controller.getPosts(principal);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testSavePost() {
        PostDTO postDTO = new PostDTO();
        postDTO.setId(1);
        postDTO.setTitle("PostDTO 1");
        postDTO.setContent("PostDTO 1 content");
        Post savedPost = new Post();
        savedPost.setId(1);
        savedPost.setTitle("Post 1");
        savedPost.setContent("Post 1 content");

        when(postService.save(principal.getName(), postDTO)).thenReturn(savedPost);
        when(postMapper.toDTO(savedPost)).thenReturn(postDTO);

        ResponseEntity<PostDTO> response = controller.savePost(principal, postDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testGetPost_Success() {
        int postId = 1;
        Post post = new Post();
        post.setTitle("Post 1");
        post.setContent("Post 1 content");
        post.setId(1);
        when(postService.get(postId)).thenReturn(post);

        ResponseEntity<PostDTO> response = controller.getPost(postId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testGetPost_NotFound() {
        when(postService.get(1)).thenThrow(new ResourceNotFoundException("Post not found"));

        ResponseEntity<PostDTO> response = controller.getPost(1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
