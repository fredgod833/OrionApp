package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.dtos.UserDTO;
import com.openclassrooms.mddapi.exceptions.ResourceNotFoundException;
import com.openclassrooms.mddapi.exceptions.UserNotFoundException;
import com.openclassrooms.mddapi.mappers.UserMapper;
import com.openclassrooms.mddapi.responses.MessageResponse;
import com.openclassrooms.mddapi.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.sql.SQLIntegrityConstraintViolationException;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping()
    @Operation(summary = "Get user", description = "Get details about current user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns a UserDTO with user details",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDTO.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content)})
    public ResponseEntity<UserDTO> getUser(Principal principal) {
        try {
            return ResponseEntity.ok(userMapper.toDTO(userService.getByEmail(principal.getName())));
        } catch (UserNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/update/{id}")
    @Operation(summary = "Update User", description = "Updates an existing user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad request - Invalid input", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content),})
    public ResponseEntity<MessageResponse> updateUser(
            @PathVariable("id") int id,
            @RequestBody @Valid UserDTO userDTO) {
        try {
            userService.update(id, userDTO);
            return ResponseEntity.ok(new MessageResponse("User updated successfully"));
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.notFound().build();
        } catch (DuplicateKeyException ex) {
            return ResponseEntity.badRequest().body(new MessageResponse(ex.getMessage()));
        }
    }

    @PostMapping("/subscribe/{id}")
    @Operation(summary = "Subscribe to a theme", description = "Subscribe a user to a theme based on theme ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User successfully subscribed to the theme",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad request - Invalid input", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "404", description = "Theme not found", content = @Content)})
    public ResponseEntity<UserDTO> subscribe(
            @PathVariable("id") int id,
            @AuthenticationPrincipal UserDetails userDetails) {
        try {
            return ResponseEntity.ok(
                    userMapper.toDTO(
                            userService.subscribeToTheme(id, userDetails.getUsername()), userDetails.getUsername()));
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/unsubscribe/{id}")
    @Operation(summary = "Unsubscribe from a theme", description = "Unsubscribes a user from a theme based on theme ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User successfully unsubscribed from the theme",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad request. Invalid ID provided", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "404", description = "Theme not found", content = @Content)})
    public ResponseEntity<UserDTO> unSubscribe(
            @PathVariable("id") int id,
            @AuthenticationPrincipal UserDetails userDetails) {
        try {
            return ResponseEntity.ok(
                    userMapper.toDTO(
                            userService.unSubscribeFromTheme(id, userDetails.getUsername())));
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().build();
        }
    }
}
