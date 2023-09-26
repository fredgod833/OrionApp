package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.model.Subscription;
import com.openclassrooms.mddapi.repository.SubscriptionRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SubscriptionService {

    private SubscriptionRepository subscriptionRepository;

    public SubscriptionService(SubscriptionRepository subscriptionRepository){
        this.subscriptionRepository = subscriptionRepository;
    }

    public List<Subscription> getSubscriptionList(){
        List<Subscription> subscriptionList = new ArrayList<>();

        try {
            subscriptionList = subscriptionRepository.findAll();
            if (!subscriptionList.isEmpty()){
                return subscriptionList;
            }
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
        return subscriptionList;
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
