package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.model.Post;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PostService {
    List<Post> postList();
    String createPost(Post post);
    ResponseEntity<?> findPostById(int post_id);
}
