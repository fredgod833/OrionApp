package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.PostDto;
import com.openclassrooms.mddapi.model.Post;
import com.openclassrooms.mddapi.model.Topic;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.PostRepository;
import com.openclassrooms.mddapi.repository.TopicRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.OffsetDateTime;
import java.util.List;

@Service
@Transactional
public class PostService {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private TopicRepository topicRepository;

    public void savePost(PostDto postDto, User user) {
        Topic topic = topicRepository.findById(postDto.getTopic_id()).orElse(null);
        if (topic != null) {
            Post post = new Post();
            post.setTitle(postDto.getTitle());
            post.setDescription(postDto.getDescription());
            post.setUser(user);
            post.setTopic(topic);
            post.setCreatedAt(OffsetDateTime.now());
            post.setUpdatedAt(OffsetDateTime.now());
            postRepository.save(post);
        }
    }

    public void updatePost(PostDto postDto) {
        Topic topic = topicRepository.findById(postDto.getTopic_id()).orElse(null);
        if (topic != null) {
            Post post = getPost(postDto.getId());
            post.setTitle(postDto.getTitle());
            post.setDescription(postDto.getDescription());
            post.setTopic(topic);
            post.setUpdatedAt(OffsetDateTime.now());
            postRepository.save(post);
        }
    }

    public Post getPost(Integer id) {
        if (postRepository.findById(id).isPresent()) {
            return postRepository.findById(id).get();
        }
        return null;
    }

    public String deletePost(Integer id, User user) {
        Post post = getPost(id);
        if (post.getUser_id().equals(user.getId())) {
            postRepository.deleteById(id);
            return "Post and related comments are deleted";
        }
        return "Not authorized to delete";
    }

    public List<Post> getAllPosts(List<String> listTopicId){
        return postRepository.findByTopicId(listTopicId);
    }
}
