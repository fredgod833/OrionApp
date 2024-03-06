package com.mddinfrastructure.comment;

import com.mddinfrastructure.request.CommentRequest;
import com.mddinfrastructure.response.ApiResponse;
import com.mddinfrastructure.response.CommentResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/comment")
public interface CommentResource {

    @GetMapping()
    CompletableFuture<List<CommentResponse>> getComments();

    @PostMapping("/create")
    CompletableFuture<ApiResponse> create(@RequestBody CommentRequest request);
}
