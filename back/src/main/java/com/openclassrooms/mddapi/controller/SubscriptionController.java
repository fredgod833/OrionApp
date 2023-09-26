package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.model.Subscription;
import com.openclassrooms.mddapi.model.dto.SubjectDto;
import com.openclassrooms.mddapi.service.SubscriptionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/subscription")
public class SubscriptionController {
    private final SubscriptionService subscriptionService;

    public SubscriptionController(SubscriptionService subscriptionService){
        this.subscriptionService = subscriptionService;
    }

    @GetMapping("/subscribed_list")
    public List<SubjectDto> getSubscriptionList(){
        return subscriptionService.getSubscribedList();
    }

    @GetMapping("/{subscription_id}")
    public ResponseEntity<?> getSubscriptionById(@PathVariable("subscription_id") int subscription_id){
        return subscriptionService.getSubscriptionById(subscription_id);
    }
}
