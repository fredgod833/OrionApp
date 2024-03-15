package com.openclassrooms.mddapi.services.implementation;

import com.openclassrooms.mddapi.dtos.PostDTO;
import com.openclassrooms.mddapi.exceptions.ResourceNotFoundException;
import com.openclassrooms.mddapi.mappers.PostMapper;
import com.openclassrooms.mddapi.models.Post;
import com.openclassrooms.mddapi.repositories.PostRepository;
import com.openclassrooms.mddapi.services.PostService;
import com.openclassrooms.mddapi.services.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {
    private final UserService userService;
    private final PostRepository postRepo;
    private final PostMapper postMapper;

    public PostServiceImpl(UserService userService, PostRepository postRepo, PostMapper postMapper) {
        this.userService = userService;
        this.postRepo = postRepo;
        this.postMapper = postMapper;
    }

    @Override
    public List<Post> getAll(String email) {
        return postRepo.getByThemeIn(userService.getByEmail(email).getSubscriptions());
    }

    @Override
    public Post get(int id) {
        return postRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post not found"));
    }

    @Override
    public Post save(String email, PostDTO postDTO) {
        return postRepo.save(postMapper.toEntity(postDTO, email));
    }
}
