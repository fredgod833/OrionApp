package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.model.Subscription;
import com.openclassrooms.mddapi.model.dto.SubjectDto;
import com.openclassrooms.mddapi.repository.SubscriptionRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final SubjectService subjectService;

    public SubscriptionServiceImpl(SubscriptionRepository subscriptionRepository, SubjectService subjectService){
        this.subscriptionRepository = subscriptionRepository;
        this.subjectService = subjectService;
    }

    public List<SubjectDto> getSubscribedList(){
        List<SubjectDto> subscribedList = new ArrayList<>();

        try {
            subscribedList = subjectService.subscribedList();

            if (subscribedList.isEmpty()){
                throw new RuntimeException("Subscribed List may be empty");
            }
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
        return subscribedList;
    }

    public ResponseEntity<?> getSubscriptionById(int subscription_id){
        Subscription subscription_identity = new Subscription();

        try {
            subscription_identity = subscriptionRepository.findById(subscription_id).orElse(null);

            if (subscription_identity == null){
                return null;
            }

            return ResponseEntity.ok(subscription_identity);
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }
}
