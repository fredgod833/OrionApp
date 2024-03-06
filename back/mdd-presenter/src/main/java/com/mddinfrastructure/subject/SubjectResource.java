package com.mddinfrastructure.subject;

import com.mddinfrastructure.response.SubjectResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/subject")
public interface SubjectResource {

    @GetMapping()
    CompletableFuture<List<SubjectResponse>> getAllSubject();

    @GetMapping("/{id}")
    CompletableFuture<SubjectResponse> getSubjectById(@PathVariable Long id);
}
