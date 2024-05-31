package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.exceptions.InvalidTopicIdException;
import com.openclassrooms.mddapi.exceptions.InvalidUserException;
import com.openclassrooms.mddapi.models.dto.TopicDto;
import com.openclassrooms.mddapi.models.payload.request.TopicRequest;
import com.openclassrooms.mddapi.models.payload.response.MessageResponse;
import com.openclassrooms.mddapi.models.payload.response.TopicListResponse;
import com.openclassrooms.mddapi.security.services.UserDetailsImpl;
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

import java.util.Collection;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/topics")
public class TopicController {

    private final TopicService topicService;

    public TopicController(TopicService topicService) {
        this.topicService = topicService;
    }

    @PostMapping(produces = APPLICATION_JSON_VALUE)
    @Operation(summary="Create new Topic.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Creation success, topic returned with id.",
                    content = @Content(schema = @Schema(implementation = TopicDto.class))
            ),
            @ApiResponse(responseCode = "400",
                    description = "Invalid request with reason message.",
                    content = @Content(schema = @Schema(implementation = MessageResponse.class))
            )})
    @SecurityRequirement(name = "Bearer JWT Authentication")
    public ResponseEntity<TopicDto> createTopic(@Valid @RequestBody TopicRequest newTopic) {
        TopicDto topic = this.topicService.createTopic(newTopic.getName(), newTopic.getDescription());
        return ResponseEntity.ok().body(topic);
    }

    @GetMapping(value="", produces = APPLICATION_JSON_VALUE)
    @Operation(summary="List existing topics.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Return list of all existing topics.",
                    content = @Content(schema = @Schema(implementation = TopicListResponse.class))
            )})
    @SecurityRequirement(name = "Bearer JWT Authentication")
    public ResponseEntity<TopicListResponse> findAll() {
        Collection<TopicDto> topics = this.topicService.findAll();
        return ResponseEntity.ok().body(new TopicListResponse(topics));
    }

    @GetMapping(value="/subscribtion", produces = APPLICATION_JSON_VALUE)
    @Operation(summary="List subsribed topics for connected user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Return list of all subsribed topics.",
                    content = @Content(schema = @Schema(implementation = TopicListResponse.class))
            )})
    @SecurityRequirement(name = "Bearer JWT Authentication")
    public ResponseEntity<TopicListResponse> findUserTopics(Authentication auth) throws InvalidUserException {
        UserDetailsImpl user = (UserDetailsImpl) auth.getPrincipal();
        Collection<TopicDto> topics = this.topicService.getAllUserTopics(user.getId());
        return ResponseEntity.ok().body(new TopicListResponse(topics));
    }

    @PostMapping("/subscribtion/{topicId}")
    @Operation(summary="User subsribed to new topic")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Subscription ok."
            )})
    @SecurityRequirement(name = "Bearer JWT Authentication")
    public ResponseEntity<?> subscribe(@PathVariable("topicId") String topicId, Authentication auth) throws InvalidUserException, InvalidTopicIdException {
        UserDetailsImpl user = (UserDetailsImpl) auth.getPrincipal();
        this.topicService.subscribeTopic(user.getId(), Integer.parseInt(topicId));
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/subscribtion/{topicId}")
    @Operation(summary="User unsubsribe to existing topic")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Unsubscription ok."
            )})
    @SecurityRequirement(name = "Bearer JWT Authentication")
    public ResponseEntity<?> unSubscribe(@PathVariable("topicId") String topicId, Authentication auth) throws InvalidUserException {
        UserDetailsImpl user = (UserDetailsImpl) auth.getPrincipal();
        this.topicService.unSubscribeTopic(user.getId(), Integer.parseInt(topicId));
        return ResponseEntity.ok().build();
    }

}
