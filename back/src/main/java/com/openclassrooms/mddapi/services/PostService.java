package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.dtos.PostDTO;
import com.openclassrooms.mddapi.models.Post;

import java.util.List;

public interface PostService {

    /**
     *
     * @return
     */
    List<Post> getAll(String email);

    /**
     *
     * @param postDTO
     * @return
     */
    Post save(String email, PostDTO postDTO);

    /**
     *
     * @param id
     * @return
     */
    Post getPost(int id);
}
