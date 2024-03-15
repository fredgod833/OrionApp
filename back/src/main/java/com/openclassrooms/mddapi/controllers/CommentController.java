package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.dtos.CommentDTO;
import com.openclassrooms.mddapi.mappers.CommentMapper;
import com.openclassrooms.mddapi.services.CommentService;
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
@RequestMapping("/api/post")
public class CommentController {

    private final CommentService commentService;
    private final CommentMapper commentMapper;

    public CommentController(CommentService commentService, CommentMapper commentMapper) {
        this.commentService = commentService;
        this.commentMapper = commentMapper;
    }

    @GetMapping("/{id}/comments")
    @Operation(summary = "Get comments", description = "Get comments associated with a specific post")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns a list of CommentDTO objects",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CommentDTO.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)})
    public ResponseEntity<List<CommentDTO>> getComments(@PathVariable int id) {
        return ResponseEntity.ok(commentService.getComments(id).stream().map(commentMapper::toDTO).toList());
    }

    @PostMapping("/{id}")
    @Operation(summary = "Save Comment", description = "Saves a new comment associated with the provided ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Comment created successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CommentDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad request, invalid comment provided", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "404", description = "Resource not found", content = @Content)})
    public ResponseEntity<CommentDTO> saveComment(
            @PathVariable("id") Integer id,
            @RequestBody @Valid CommentDTO commentDTO,
            Principal principal) {
        return ResponseEntity.ok(
                commentMapper.toDTO(
                        commentService.saveComment(
                                commentMapper.toEntity(id, commentDTO, principal.getName()))));
    }
}
