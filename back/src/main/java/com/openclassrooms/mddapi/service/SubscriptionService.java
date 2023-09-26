package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.model.Subscription;
import com.openclassrooms.mddapi.model.dto.SubjectDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SubscriptionService {
    List<SubjectDto> getSubscribedList();
    ResponseEntity<?> getSubscriptionById(int subscription_id);
}
