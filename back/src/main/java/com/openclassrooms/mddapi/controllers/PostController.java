package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.dtos.PostDTO;
import com.openclassrooms.mddapi.exceptions.ResourceNotFoundException;
import com.openclassrooms.mddapi.exceptions.UserNotFoundException;
import com.openclassrooms.mddapi.mappers.PostMapper;
import com.openclassrooms.mddapi.services.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;
    private final PostMapper postMapper;

    public PostController(PostService postService, PostMapper postMapper) {
        this.postService = postService;
        this.postMapper = postMapper;
    }

    @GetMapping()
    @Operation(summary = "Get all posts", description = "Get a list of all posts based on user subscriptions")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns a list of PostDTO objects",
                    content = @Content(mediaType = "application/json",
                            schema =  @Schema(implementation = PostDTO.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content)})
    public ResponseEntity<List<PostDTO>> getPosts(Principal principal) {
        try {
            return ResponseEntity.ok(postMapper.toDTO(postService.getAll(principal.getName())));
        } catch (UserNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Post by ID", description = "Get details of a post by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns a PostDTO with post details",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PostDTO.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "404", description = "Post not found", content = @Content)})
    public ResponseEntity<PostDTO> getPost(@PathVariable("id") int id) {
        try {
            return ResponseEntity.ok(postMapper.toDTO(postService.get(id)));
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping()
    @Operation(summary = "Save a new post", description = "Creates a new post")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Post created successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PostDTO.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),})
    public ResponseEntity<PostDTO> savePost(Principal principal, @RequestBody @Valid PostDTO postDTO) {
        return ResponseEntity.ok(postMapper.toDTO(postService.save(principal.getName(), postDTO)));
    }
}
