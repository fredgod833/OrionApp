package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.exceptions.*;
import com.openclassrooms.mddapi.mapper.CommentMapper;
import com.openclassrooms.mddapi.mapper.PostMapper;
import com.openclassrooms.mddapi.models.dto.CommentDto;
import com.openclassrooms.mddapi.models.dto.PostDto;
import com.openclassrooms.mddapi.models.entities.CommentEntity;
import com.openclassrooms.mddapi.models.entities.PostEntity;
import com.openclassrooms.mddapi.models.entities.TopicEntity;
import com.openclassrooms.mddapi.models.entities.UserEntity;
import com.openclassrooms.mddapi.repository.CommentRepository;
import com.openclassrooms.mddapi.repository.PostRepository;
import com.openclassrooms.mddapi.repository.TopicRepository;
import com.openclassrooms.mddapi.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class PostService {

    private final PostRepository postRepository;

    private final TopicRepository topicRepository;

    private final UserRepository userRepository;

    private final CommentRepository commentRepository;

    private final PostMapper postMapper;

    private final CommentMapper commentMapper;

    public PostService(PostRepository postRepository,
                       TopicRepository topicRepository,
                       UserRepository userRepository,
                       CommentRepository commentRepository,
                       PostMapper postMapper,
                       CommentMapper commentMapper) {

        this.postRepository = postRepository;
        this.topicRepository = topicRepository;
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
        this.postMapper = postMapper;
        this.commentMapper = commentMapper;
    }

    public PostDto createPost(final String title, final String content, final int topicId, final int authorId) throws InvalidTopicIdException {

        TopicEntity topic = this.topicRepository.findById(topicId).orElseThrow(() -> new InvalidTopicIdException(String.format("identifiant de theme %s non trouvé.",authorId)));
        UserEntity author = this.userRepository.findById(authorId).orElseThrow(() -> new UsernameNotFoundException(String.format("utilisateur %s non trouvé.",topicId)));

        PostEntity post = new PostEntity();
        post.setTitle(title);
        post.setContent(content);
        post.setTopic(topic);
        post.setAuthor(author);

        return postMapper.toDto(this.postRepository.save(post));
    }

    public PostDto updatePost(final int postId, final String title, final String content, final int topicId, final int authorId) throws InvalidAuthorException, InvalidPostIdException, InvalidTopicIdException {

        PostEntity post = this.postRepository.findById(postId)
                .orElseThrow(() -> new InvalidPostIdException(String.format("article %s non trouvé.",postId)));

        TopicEntity topic = this.topicRepository.findById(topicId)
                .orElseThrow(() -> new InvalidTopicIdException(String.format("theme %s non trouvé.",topicId)));

        if (post.getAuthor().getId() != authorId) {
            throw new InvalidAuthorException("Auteur non valide.");
        }

        post.setTitle(title);
        post.setContent(content);
        post.setTopic(topic);

        this.postRepository.save(post);
        return postMapper.toDto(post);
    }

    public Collection<PostDto> listPostsByTopicId(final int topicId) {
        Collection<PostEntity> postEntities = this.postRepository.findPostEntitiesByTopicId(topicId);
        return postMapper.toDto(postEntities);
    }

    public Collection<CommentDto> listPostsComments(final int postId) {

        Collection<CommentEntity> comments = this.commentRepository.findCommentEntitiesByPostId(postId);
        return commentMapper.toDto(comments);
    }

    public CommentDto addComment(final int postId, final int readerId, final String comment) throws InvalidPostIdException {

        UserEntity reader = this.userRepository.findById(readerId)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("utilisateur %s non trouvé.",readerId)));

        boolean postExists = this.postRepository.existsById(postId);
        if (!postExists) {
            throw new InvalidPostIdException(String.format("article %s non trouvé.",postId));
        }

        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setPostId(postId);
        commentEntity.setUser(reader);
        commentEntity.setContent(comment);

        commentEntity = commentRepository.save(commentEntity);

        return commentMapper.toDto(commentEntity);
    }

    public CommentDto updateComment(final int postId, final int commentId, final int readerId, final String comment) throws InvalidPostIdException, InvalidUserException, InvalidCommentIdException {

        CommentEntity commentEntity = this.commentRepository.findById(commentId)
                .orElseThrow(() -> new InvalidCommentIdException(String.format("commentaire %s non trouvé.",readerId)));

        if (postId != commentEntity.getPostId()) {
            throw new InvalidUserException(String.format("le commentaire %1$s ne porte pas sur l'article %2$s", commentId, postId));
        }

        if (readerId != commentEntity.getUser().getId()) {
            throw new InvalidUserException(String.format("%1$s n'est pas l'auteur du commentaire %2$s", readerId, commentId));
        }

        commentEntity.setContent(comment);
        commentEntity = commentRepository.save(commentEntity);

        return commentMapper.toDto(commentEntity);
    }

    public void deleteComment(final int postId, final int commentId, final int readerId) throws InvalidPostIdException, InvalidUserException {

        CommentEntity commentEntity = this.commentRepository.findById(commentId)
                .orElseThrow(() -> new InvalidPostIdException(String.format("utilisateur %s non trouvé.",readerId)));

        if (readerId != commentEntity.getUser().getId()) {
            throw new InvalidUserException(String.format("%1$s n'est pas l'auteur du commentaire %2$s", readerId, commentId));
        }

        if (postId != commentEntity.getPostId()) {
            throw new InvalidPostIdException(String.format("ce commentaire %1$s n'' est pas lié a l'article %2$s", commentId, postId));
        }

        commentRepository.delete(commentEntity);
    }










}
