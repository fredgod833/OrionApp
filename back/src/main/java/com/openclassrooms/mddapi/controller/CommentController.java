package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.dto.CommentDto;
import com.openclassrooms.mddapi.dto.ResponseDto;
import com.openclassrooms.mddapi.model.Comment;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.service.CommentService;
import com.openclassrooms.mddapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {
    @Autowired
    UserService userService;

    @Autowired
    CommentService commentService;
    private final ResponseDto responseDto = new ResponseDto();
    @PostMapping(
            value ="",
            consumes = { MediaType.MULTIPART_FORM_DATA_VALUE },
            produces = MediaType.APPLICATION_JSON_VALUE
        )
    public ResponseDto createTopic(
            @ModelAttribute CommentDto commentDto,
            Authentication authentication
        ) {
        User user = userService.getUserByEmail(authentication.getName());
        commentService.saveComment(commentDto, user);
        responseDto.setResponse("Comment created !");
        return responseDto;
    }

    @GetMapping(value = "/post/{id}", produces = { "application/json" })
    public ResponseEntity<List<Comment>> getAllCommentsPost(@PathVariable Integer id){
        List<Comment> comments = this.commentService.getAllComments(id);
        return ResponseEntity.ok().body(comments);
    }
}
