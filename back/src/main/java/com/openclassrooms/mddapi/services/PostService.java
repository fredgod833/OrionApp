package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.dto.PostDto;
import com.openclassrooms.mddapi.models.Post;
import com.openclassrooms.mddapi.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    private PostDto convertToDto(Post post) {
        PostDto postDto = new PostDto();

        postDto.setId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setContent(post.getContent());
        postDto.setCreatedAt(post.getCreatedAt());
        postDto.setUpdatedAt(post.getUpdatedAt());

        postDto.setUserId(post.getUser().getId());
        postDto.setTopicId(post.getTopic().getId());

        return postDto;
    }

    private Post convertToEntity(PostDto postDto) {
        Post post = new Post();

        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());

        return post;
    }

    public List<PostDto> findAllPosts() {
        return postRepository.findAll().stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public PostDto findPostById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new RuntimeException("Post not found"));
        return convertToDto(post);
    }

    public PostDto createPost(PostDto postDto) {
        Post post = convertToEntity(postDto);
        Post savedPost = postRepository.save(post);
        return convertToDto(savedPost);
    }

    public PostDto updatePost(Long id, PostDto postDto) {
        Post existingPost = postRepository.findById(id).orElseThrow(() -> new RuntimeException("Post not found"));
        existingPost.setTitle(postDto.getTitle());
        existingPost.setContent(postDto.getContent());


        Post updatedPost = postRepository.save(existingPost);
        return convertToDto(updatedPost);
    }

    public void deletePost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new RuntimeException("Post not found"));
        postRepository.delete(post);
    }
}
