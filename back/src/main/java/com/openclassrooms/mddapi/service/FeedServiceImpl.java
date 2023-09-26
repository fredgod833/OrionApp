package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.model.Feed;
import com.openclassrooms.mddapi.model.Subject;
import com.openclassrooms.mddapi.model.Subscription;
import com.openclassrooms.mddapi.model.dto.SubjectDto;
import com.openclassrooms.mddapi.repository.FeedRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FeedServiceImpl implements FeedService{
    private final FeedRepository feedRepository;
    public FeedServiceImpl(FeedRepository feedRepository){
        this.feedRepository = feedRepository;
    }
    public List<Feed> findFeedList(){

        return feedRepository.findAll();
    }

    // TODO: 22/09/2023 should i remove this business logic
    public List<SubjectDto>findSubjectListSubscribed(){
        List<Feed> feedList = new ArrayList<>();
        List<SubjectDto> subjectDtoList = new ArrayList<>();

        try {
            feedList = feedRepository.findAll();
            if (feedList.isEmpty()){
                throw new RuntimeException("Feed List may be empty!!!");
            }
            for (Feed feed : feedList) {
                for (Subscription subscription : feed.getSubscriptionList()) {
                    for (Subject subject: subscription.getSubjectList()) {
                        SubjectDto subjectDto = SubjectDto.builder()
                                .idSubject(subject.getIdSubject())
                                .title(subject.getTitle())
                                .description(subject.getDescription())
                                .isSubscribed(subject.getIsSubscribed())
                                .build();

                        subjectDtoList.add(subjectDto);
                    }
                }
            }
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
        return subjectDtoList;
    }
}
