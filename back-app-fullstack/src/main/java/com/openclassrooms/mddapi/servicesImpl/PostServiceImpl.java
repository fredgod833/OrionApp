package com.openclassrooms.mddapi.servicesImpl;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.openclassrooms.mddapi.dto.PostDto;
import com.openclassrooms.mddapi.models.Post;
import com.openclassrooms.mddapi.models.Topic;
import com.openclassrooms.mddapi.models.UserEntity;
import com.openclassrooms.mddapi.repositories.PostRepository;
import com.openclassrooms.mddapi.services.PostService;
import com.openclassrooms.mddapi.services.UserIService;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserIService userService;

    // @Autowired
    // private ModelMapper modelMapper;

    // @Override
    // public PostDto createPostDto(String title, UserEntity user, Date date, String
    // content, Topic topic) {
    // UserEntity currentUser = userService.getCurrentUser(user.getId());

    // Post post = new Post();
    // post.setTitle(title);
    // post.setUser(currentUser);
    // post.setDate(date);
    // post.setContent(content);
    // post.setTopic(topic);
    // post.setCreatedAt(LocalDateTime.now());
    // post.setUpdatedAt(LocalDateTime.now());

    // Post postSaved = postRepository.save(post);
    // PostDto postDto = modelMapper.map(postSaved, PostDto.class);
    // return postDto;
    // }

    // @Override
    // public PostDto createPostDto(PostDto postDto) {
    // UserEntity currentUser = userService.getCurrentUser(postDto.getUser_id());

    // Post post = new Post();
    // post.setTitle(postDto.getTitle());
    // post.setUser(currentUser);
    // post.setDate(postDto.getDate());
    // post.setContent(postDto.getContent());
    // post.setTopic(postDto.getTopic_id());
    // post.setCreatedAt(LocalDateTime.now());
    // post.setUpdatedAt(LocalDateTime.now());

    // Post postSaved = postRepository.save(post);
    // PostDto savedPostDto = modelMapper.map(postSaved, PostDto.class);
    // return savedPostDto;
    // }

    @Override
    public Post getPostById(Long id) {
        Post post = postRepository.findById(id).get();

        // PostDto postDto = modelMapper.map(post, PostDto.class);
        return post;
    }

    // @Override
    // public List<PostDto> getAllPosts() {
    // List<Post> posts = postRepository.findAll();

    // List<PostDto> postsDto = posts.stream().map(post -> modelMapper.map(post,
    // PostDto.class))
    // .collect(Collectors.toList());
    // return postsDto;
    // }
    @Override
    public Post createPost(Post post) {
        return this.postRepository.save(post);
    }

    @Override
    public List<Post> getAllPosts() {
        return this.postRepository.findAll();
    }

}
