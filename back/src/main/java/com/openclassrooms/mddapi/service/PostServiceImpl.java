package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.model.Post;
import com.openclassrooms.mddapi.model.Subject;
import com.openclassrooms.mddapi.repository.PostRepository;
import com.openclassrooms.mddapi.repository.SubjectRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final SubjectService subjectService;

    private final SubjectRepository subjectRepository;
    public PostServiceImpl(PostRepository postRepository, SubjectService subjectService, SubjectRepository subjectRepository) {
        this.postRepository = postRepository;
        this.subjectService = subjectService;
        this.subjectRepository = subjectRepository;
    }

    public List<Post> postList() {
        List<Post> postArray = new ArrayList<>();

        try {
            postArray = postRepository.findAll();

            if (postArray.isEmpty()) {
                throw new RuntimeException("List of post could be empty!!!");
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return postArray;
    }

    public ResponseEntity<?> findPostById(int post_id) {
        Post post = new Post();

        try {
            post = postRepository.findById(post_id).orElse(null);

            if (post == null) {
                throw new RuntimeException("Post may be null");
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

        return ResponseEntity.ok(post);
    }

    @Override
    public Post createPost(Post post, int id_subject) {

        try {

            // identify the subject
            Subject subject = subjectService.getSubjectById(id_subject);

            //verify if subject is null
            if (subject == null) {
                return null;
            }

            // build the post
            Post postBuilt = Post.builder()
                    .title(post.getTitle())
                    .content(post.getContent())
                    .author(post.getAuthor())
                    .date(post.getDate())
                    .comments(post.getComments())
                    .build();

            // verify if post is null
            if (postBuilt == null){
                return null;
            }

            // if not add post to list
            subject.getPostList().add(postBuilt);

            //save subject
            subjectRepository.save(subject);

            return postBuilt;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}