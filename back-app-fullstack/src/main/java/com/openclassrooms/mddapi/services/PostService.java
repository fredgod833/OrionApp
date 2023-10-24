package com.openclassrooms.mddapi.services;

import java.util.Date;
import java.util.List;
import com.openclassrooms.mddapi.dto.PostDto;
import com.openclassrooms.mddapi.models.Post;
import com.openclassrooms.mddapi.models.Topic;
import com.openclassrooms.mddapi.models.UserEntity;

public interface PostService {

    // public PostDto createPostDto(String title, UserEntity User, Date date, String
    // content, Topic topic);

    public Post createPost(Post post);

    public Post getPostById(Long id);

    public List<Post> getAllPosts();

}
