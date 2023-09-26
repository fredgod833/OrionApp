package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.model.Feed;
import com.openclassrooms.mddapi.model.dto.SubjectDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FeedService {
    List<Feed>findFeedList();
    List<SubjectDto>findSubjectListSubscribed();
}
