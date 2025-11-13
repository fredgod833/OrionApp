package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.exceptions.*;
import com.openclassrooms.mddapi.models.dto.CommentDto;
import com.openclassrooms.mddapi.models.dto.PostDto;
import com.openclassrooms.mddapi.models.dto.TopicDto;
import com.openclassrooms.mddapi.models.payload.request.CommentRequest;
import com.openclassrooms.mddapi.models.payload.request.PostRequest;
import com.openclassrooms.mddapi.models.payload.response.MessageResponse;
import com.openclassrooms.mddapi.models.payload.response.CommentsListResponse;
import com.openclassrooms.mddapi.models.payload.response.PostListResponse;
import com.openclassrooms.mddapi.security.services.UserDetailsImpl;
import com.openclassrooms.mddapi.services.PostService;
import com.openclassrooms.mddapi.services.TopicService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/posts")
public class PostController {

    PostService postService;
    TopicService topicService;

    PostController(PostService postService, TopicService topicService) {
        this.postService = postService;
        this.topicService = topicService;
    }

    @PostMapping(produces = APPLICATION_JSON_VALUE)
    @Operation(summary="Publish new Post.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Creation success, post returned with id.",
                    content = @Content(schema = @Schema(implementation = PostDto.class))
            ),
            @ApiResponse(responseCode = "400",
                    description = "Invalid request with reason message.",
                    content = @Content(schema = @Schema(implementation = MessageResponse.class))
            )})
    @SecurityRequirement(name = "Bearer JWT Authentication")
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostRequest newPost, Authentication auth) throws InvalidTopicIdException {
        UserDetailsImpl connectedUser = (UserDetailsImpl) auth.getPrincipal();
        PostDto post = this.postService.createPost(newPost.getTitle(), newPost.getContent(), newPost.getTopicId(), connectedUser.getId());
        return ResponseEntity.ok().body(post);
    }

    @PutMapping(value="/{postId}", produces = APPLICATION_JSON_VALUE)
    @Operation(summary="update existing Post.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Update success,confirm message returned.",
                    content = @Content(schema = @Schema(implementation = PostDto.class))
            ),
            @ApiResponse(responseCode = "400",
                    description = "Invalid request with reason message.",
                    content = @Content(schema = @Schema(implementation = MessageResponse.class))
            )})
    @SecurityRequirement(name = "Bearer JWT Authentication")
    public ResponseEntity<PostDto> update(@PathVariable("postId") String postId, @Valid @RequestBody PostRequest postUpdate, Authentication auth) throws InvalidTopicIdException, InvalidPostIdException, InvalidAuthorException {
        UserDetailsImpl connectedUser = (UserDetailsImpl) auth.getPrincipal();
        PostDto post = this.postService.updatePost(Integer.parseInt(postId), postUpdate.getTitle(), postUpdate.getContent(), postUpdate.getTopicId(), connectedUser.getId());
        return ResponseEntity.ok().body(post);
    }

    @GetMapping(value="/{topicId}", produces = APPLICATION_JSON_VALUE)
    @Operation(summary="List All Posts for a topic.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Creation success, post list.",
                    content = @Content(schema = @Schema(implementation = PostListResponse.class))
            ),
            @ApiResponse(responseCode = "400",
                    description = "Invalid request with reason message.",
                    content = @Content(schema = @Schema(implementation = MessageResponse.class))
            )})
    @SecurityRequirement(name = "Bearer JWT Authentication")
    public ResponseEntity<PostListResponse> listTopicPosts(@PathVariable("topicId") String topicId) {
        Collection<PostDto> posts = this.postService.listPostsByTopicId(Integer.parseInt(topicId));
        return ResponseEntity.ok().body(new PostListResponse(posts));
    }

    @GetMapping(value="/subscribed", produces = APPLICATION_JSON_VALUE)
    @Operation(summary="List All Posts for connected User (from his subscriptions).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Creation success, post list.",
                    content = @Content(schema = @Schema(implementation = PostListResponse.class))
            ),
            @ApiResponse(responseCode = "400",
                    description = "Invalid request with reason message.",
                    content = @Content(schema = @Schema(implementation = MessageResponse.class))
            )})
    @SecurityRequirement(name = "Bearer JWT Authentication")
    public ResponseEntity<PostListResponse> listPosts(Authentication auth) throws InvalidUserException {
        UserDetailsImpl user = (UserDetailsImpl) auth.getPrincipal();
        Collection<TopicDto> topics = this.topicService.getSubscribedTopics(user.getId());

        PostListResponse result = new PostListResponse(new ArrayList<>());
        for (TopicDto topic : topics) {
            Collection<PostDto> posts = this.postService.listPostsByTopicId(topic.getId());
            result.getPosts().addAll(posts);
        }
        return ResponseEntity.ok().body(result);
    }

    @GetMapping(value="/{postId}/comments", produces = APPLICATION_JSON_VALUE)
    @Operation(summary="List All Posts comments for one Post.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Creation success, post list.",
                    content = @Content(schema = @Schema(implementation = CommentsListResponse.class))
            ),
            @ApiResponse(responseCode = "400",
                    description = "Invalid request with reason message.",
                    content = @Content(schema = @Schema(implementation = MessageResponse.class))
            )})
    @SecurityRequirement(name = "Bearer JWT Authentication")
    public ResponseEntity<CommentsListResponse> listComments(@PathVariable("postId") String postId) {
        Collection<CommentDto> comments = this.postService.listPostsComments(Integer.parseInt(postId));
        return ResponseEntity.ok().body(new CommentsListResponse(comments));
    }

    @PostMapping(value="/{postId}/comments", produces = APPLICATION_JSON_VALUE)
    @Operation(summary="Post new comments for one Post.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Creation success, recorded comment.",
                    content = @Content(schema = @Schema(implementation = CommentDto.class))
            ),
            @ApiResponse(responseCode = "400",
                    description = "Invalid request with reason message.",
                    content = @Content(schema = @Schema(implementation = MessageResponse.class))
            )})
    @SecurityRequirement(name = "Bearer JWT Authentication")
    public ResponseEntity<CommentDto> createComment(@PathVariable("postId") String postId, @Valid @RequestBody CommentRequest commentRequest, Authentication auth) throws InvalidPostIdException {
        UserDetailsImpl user = (UserDetailsImpl) auth.getPrincipal();
        CommentDto comment = this.postService.addComment(Integer.parseInt(postId), user.getId(), commentRequest.getComment());
        return ResponseEntity.ok().body(comment);
    }

    @PutMapping(value="/{postId}/comments/{commentId}", produces = APPLICATION_JSON_VALUE)
    @Operation(summary="Update comment for one Post.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Creation success, post list.",
                    content = @Content(schema = @Schema(implementation = CommentDto.class))
            ),
            @ApiResponse(responseCode = "400",
                    description = "Invalid request with reason message.",
                    content = @Content(schema = @Schema(implementation = MessageResponse.class))
            )})
    @SecurityRequirement(name = "Bearer JWT Authentication")
    public ResponseEntity<CommentDto> updateComment(@PathVariable("postId") String postId, @PathVariable("commentId") String commentId, @Valid @RequestBody CommentRequest commentRequest, Authentication auth) throws InvalidPostIdException, InvalidUserException, InvalidCommentIdException {
        UserDetailsImpl user = (UserDetailsImpl) auth.getPrincipal();
        CommentDto comment = this.postService.updateComment(
                Integer.parseInt(postId),
                Integer.parseInt(commentId),
                user.getId(),
                commentRequest.getComment());
        return ResponseEntity.ok().body(comment);
    }

    @DeleteMapping(value="/{postId}/comments/{commentId}", produces = APPLICATION_JSON_VALUE)
    @Operation(summary="Post new comments for one Post.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Creation success, post list.",
                    content = @Content(schema = @Schema(implementation = MessageResponse.class))
            ),
            @ApiResponse(responseCode = "400",
                    description = "Invalid request with reason message.",
                    content = @Content(schema = @Schema(implementation = MessageResponse.class))
            )})
    @SecurityRequirement(name = "Bearer JWT Authentication")
    public ResponseEntity<MessageResponse> deleteComment(@PathVariable("postId") String postId, @PathVariable("commentId") String commentId, Authentication auth) throws InvalidPostIdException, InvalidUserException {
        UserDetailsImpl user = (UserDetailsImpl) auth.getPrincipal();
        this.postService.deleteComment(
                Integer.parseInt(postId),
                Integer.parseInt(commentId),
                user.getId()
        );
        return ResponseEntity.ok().body(new MessageResponse("commentaire supprimé."));
    }

}
