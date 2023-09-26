package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.model.Feed;
import com.openclassrooms.mddapi.model.dto.SubjectDto;
import com.openclassrooms.mddapi.service.FeedService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/feed")
public class FeedController {
    private FeedService feedService;
    public FeedController (FeedService feedService){
        this.feedService = feedService;
    }
    @GetMapping("/{id_user}")
    public List<Feed> getFeedList(){
        return feedService.findFeedList();
    }

    @GetMapping("/feed_list")
    public List<Feed> getFeedSubscriptionList(){
        return feedService.findFeedList();
    }
    @GetMapping("/subscribe_subjectDto_list")
    public List<SubjectDto>findSubjectListSubscribed(){
        return feedService.findSubjectListSubscribed();
    }
}
