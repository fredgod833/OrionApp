package com.mddinfrastructure.subscription;

import com.mddinfrastructure.response.ApiResponse;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/subscription")
interface SubscriptionResource {

    @PostMapping("/{userId}/{subjectId}")
    CompletableFuture<ApiResponse> saveSubscription(@PathVariable Long userId,
                                                    @PathVariable Long subjectId);

    @DeleteMapping("/{subscriptionId}")
    CompletableFuture<ApiResponse> removeSubscription(@PathVariable Long subscriptionId);
}
