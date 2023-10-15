package com.openclassrooms.mddapi.services;

import java.util.Date;
import java.util.List;
import com.openclassrooms.mddapi.dto.PostDto;
import com.openclassrooms.mddapi.models.Topic;
import com.openclassrooms.mddapi.models.UserEntity;

public interface PostService {

    public PostDto createPostDto(String title, UserEntity User, Date date, String content, Topic topic);

    public PostDto getPostById(Long id);

    public List<PostDto> getAllPosts();

}
