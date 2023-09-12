package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.dto.ResponseDto;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.service.SubscriptionService;
import com.openclassrooms.mddapi.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@RestController
@RequestMapping("/api/subscriptions")
public class SubscriptionController {
    @Autowired
    SubscriptionService subscriptionService;

    @Autowired
    UserService userService;

    private final ResponseDto responseDto = new ResponseDto();

    @PostMapping(value ="/topic/{id}", produces = { "application/json" })
    public ResponseDto createSubscription(
        @PathVariable Integer id,
        Authentication authentication
        ) {
            User user = userService.getUserByEmail(authentication.getName());
            subscriptionService.saveSubscription(id, user);
            responseDto.setResponse("Subscription created !");
            return responseDto;
    }

    @DeleteMapping(value = "/topic/{id}", produces = { "application/json" })
    public ResponseDto deleteSubscription(@PathVariable Integer id, Authentication authentication){
        User user = userService.getUserByEmail(authentication.getName());
        try {
            subscriptionService.deleteSubscription(id, user);
            responseDto.setResponse("success");
            return responseDto;
        } catch (Exception e) {
            responseDto.setResponse("error");
            return responseDto;
        }
    }

    @GetMapping(value = "/topic_ids", produces = { "application/json" })
    public List<Integer> getAllTopicsSubscriptionIds(Authentication authentication){
        User user = userService.getUserByEmail(authentication.getName());
        return subscriptionService.getAllIdsTopics(user.getId());
    }
}

