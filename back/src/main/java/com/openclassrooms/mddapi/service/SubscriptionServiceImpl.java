package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.model.Subject;
import com.openclassrooms.mddapi.model.Subscription;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.model.dto.SubjectDto;
import com.openclassrooms.mddapi.repository.SubjectRepository;
import com.openclassrooms.mddapi.repository.SubscriptionRepository;
import com.openclassrooms.mddapi.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final SubjectService subjectService;
    private final JwtDecoder decoder;
    private final UserService userService;

    private final UserRepository userRepository;

    private final SubjectRepository subjectRepository;

    public SubscriptionServiceImpl(SubscriptionRepository subscriptionRepository, SubjectService subjectService, UserService userService, JwtDecoder decoder, UserRepository userRepository, SubjectRepository subjectRepository) {
        this.subscriptionRepository = subscriptionRepository;
        this.subjectService = subjectService;
        this.userService = userService;
        this.decoder = decoder;
        this.userRepository = userRepository;
        this.subjectRepository = subjectRepository;
    }

    public List<SubjectDto> getSubscribedList() {
        List<SubjectDto> subscribedList = new ArrayList<>();

        try {
            //subscribedList = subjectService.subscribedList();

            if (subscribedList.isEmpty()) {
                throw new RuntimeException("Subscribed List may be empty");
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return subscribedList;
    }

    public ResponseEntity<?> getSubscriptionById(int subscription_id) {
        Subscription subscription_identity = new Subscription();

        try {
            subscription_identity = subscriptionRepository.findById(subscription_id).orElse(null);

            if (subscription_identity == null) {
                return null;
            }

            return ResponseEntity.ok(subscription_identity);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public List<Subscription> subscriptions() {
        return subscriptionRepository.findAll();
    }

    @Override
    public Subscription subscribe(HttpServletRequest request, int id) {
        Subscription subscriptions = new Subscription();

        //Identify subject
        Subject subject = subjectService.getSubjectById(id);

        //Parse token to user email
        String email = interceptor(request);

        // Return user by its email
        User user = userService.getByEmail(email);

        return subscriptions;
    }

    @Override
    public ResponseEntity<Subscription> unsubscribe(HttpServletRequest request, int id) {

        //Identify email
        String email = interceptor(request);

        //Identify user
        User user = userService.getByEmail(email);

        //Identify subject
        Subject subject = subjectService.getSubjectById(id);

        return ResponseEntity.ok(new Subscription());
    }

    private String interceptor(HttpServletRequest request){

        String auth = request.getHeader("Authorization");

        if (StringUtils.hasText(auth) && auth.startsWith("Bearer")){
            String token = auth.substring(7);

            String email = decoder.decode(token).getSubject();
            return email;
        }
        return "Error while intercepting";
    }
}