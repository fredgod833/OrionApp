package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.model.Post;
import com.openclassrooms.mddapi.model.Subject;
import com.openclassrooms.mddapi.model.dto.SubjectDto;
import com.openclassrooms.mddapi.repository.PostRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final SubjectService subjectService;

    public PostServiceImpl(PostRepository postRepository, SubjectService subjectService) {
        this.postRepository = postRepository;
        this.subjectService = subjectService;
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
    public String createPost(Post post) {

        try {
            //choose a subject in the subject list
            List<SubjectDto> subjectDtoList = new ArrayList<>();
            subjectDtoList = subjectService.findSubjectDtoList();

            //verify subject is present in the subject list
            if (subjectDtoList.isEmpty()) {
                throw new RuntimeException("SubjectDto list may be empty");
            }

            //verify if post is not null
            if (post == null) {
                return null;
            }

            //identify the subject of choice
            for (SubjectDto subject : subjectDtoList) {

                //build post
                if (post.getSubject().getIdSubject() == subject.getIdSubject()) {

                    Subject sub = Subject.builder()
                            .idSubject(subject.getIdSubject())
                            .title(subject.getTitle())
                            .description(subject.getDescription())
                            .isSubscribed(subject.getIsSubscribed())
                            .build();
                    Post buildPost = Post.builder()
                            .id_post(post.getId_post())
                            .title(post.getTitle())
                            .content(post.getContent())
                            .author(post.getAuthor())
                            .date(post.getDate())
                            .comments(post.getComments())
                            .subject(sub)
                            .build();

                    //create post
                    postRepository.save(buildPost);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        //return message if post was created
        return "Post created !!!";
    }
}