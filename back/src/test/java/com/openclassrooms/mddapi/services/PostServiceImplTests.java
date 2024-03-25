package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.dtos.PostDTO;
import com.openclassrooms.mddapi.exceptions.ResourceNotFoundException;
import com.openclassrooms.mddapi.mappers.PostMapper;
import com.openclassrooms.mddapi.models.Post;
import com.openclassrooms.mddapi.models.Theme;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.repositories.PostRepository;
import com.openclassrooms.mddapi.services.implementation.PostServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class PostServiceImplTests {

    private UserService userService;
    private PostRepository postRepository;
    private PostMapper postMapper;
    private PostService postService;

    @BeforeEach
    void setUp() {
        userService = mock(UserService.class);
        postRepository = mock(PostRepository.class);
        postMapper = mock(PostMapper.class);
        postService = new PostServiceImpl(userService, postRepository, postMapper);
    }

    @Test
    void testGetAll() {
        String userEmail = "test@example.com";
        List<Theme> subscriptions = new ArrayList<>();
        subscriptions.add(new Theme());
        subscriptions.add(new Theme());

        User user = new User();
        user.setEmail(userEmail);
        user.setSubscriptions(subscriptions);

        List<Post> expectedPosts = new ArrayList<>();

        when(userService.getByEmail(userEmail)).thenReturn(user);
        when(postRepository.getByThemeIn(subscriptions)).thenReturn(expectedPosts);

        List<Post> retrievedPosts = postService.getAll(userEmail);

        assertEquals(expectedPosts, retrievedPosts);
        verify(userService, times(1)).getByEmail(userEmail);
        verify(postRepository, times(1)).getByThemeIn(subscriptions);
    }

    @Test
    void testGetPost_Success() {
        int postId = 1;
        Post expectedPost = new Post();

        when(postRepository.findById(postId)).thenReturn(Optional.of(expectedPost));

        Post retrievedPost = postService.get(postId);

        assertEquals(expectedPost, retrievedPost);
        verify(postRepository, times(1)).findById(postId);
    }

    @Test
    void testGetPost_PostNotFound() {
        int nonExistingPostId = 999;

        when(postRepository.findById(nonExistingPostId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> postService.get(nonExistingPostId));
        verify(postRepository, times(1)).findById(nonExistingPostId);
    }


    @Test
    void testSave() {
        String email = "user@example.com";
        PostDTO postDTO = new PostDTO();
        postDTO.setTitle("Title");
        postDTO.setContent("Content");
        Post post = new Post();
        post.setTitle("Title");
        post.setContent("Content");

        when(postMapper.toEntity(postDTO, email)).thenReturn(post);
        when(postRepository.save(post)).thenReturn(post);

        Post savedPost = postService.save(email, postDTO);

        verify(postMapper, times(1)).toEntity(postDTO, email);
        verify(postRepository, times(1)).save(post);
        assertEquals(post, savedPost);
    }
}
