package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.models.Post;
import com.openclassrooms.mddapi.repository.PostRepository;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class PostService {

  private final PostRepository postRepository;

  public PostService(PostRepository postRepository) {
    this.postRepository = postRepository;
  }

  public void delete(Long id) {
    this.postRepository.deleteById(id);
  }

  public Post findById(Long id) {
    return this.postRepository.findById(id).orElse(null);
  }

  public List<Post> findAll() {
    return this.postRepository.findAll();
  }

  public Post create(Post post) {
    return this.postRepository.save(post);
  }


}
