package com.mddinfrastructure.article;

import com.mddinfrastructure.request.ArticleRequest;
import com.mddinfrastructure.response.ApiResponse;
import com.mddinfrastructure.response.ArticleResponse;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/article")
public interface ArticleResource {

    @GetMapping()
    CompletableFuture<List<ArticleResponse>> getAllArticles();

    @GetMapping("/{id}")
    CompletableFuture<ArticleResponse> getArticleById(@PathVariable Long id);

    @PostMapping("/create")
    CompletableFuture<ApiResponse> saveArticle(@RequestBody ArticleRequest articleRequest);
}
