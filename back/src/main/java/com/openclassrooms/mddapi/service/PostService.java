package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.model.Post;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

//Interface to implement
@Service
public interface PostService {
    List<Post> postList();
    Post createPost(Post post, int id_subject);
    ResponseEntity<?> findPostById(int post_id);
}
