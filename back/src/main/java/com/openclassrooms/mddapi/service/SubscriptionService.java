package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.model.Subject;
import com.openclassrooms.mddapi.model.Subscription;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.model.dto.SubjectDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public interface SubscriptionService {
    List<SubjectDto> getSubscribedList();
    ResponseEntity<?> getSubscriptionById(int subscription_id);

    List<Subscription> subscriptions();
    Subscription subscribe(HttpServletRequest request, int id);
    ResponseEntity<Subscription> unsubscribe(HttpServletRequest request, int id);

}
