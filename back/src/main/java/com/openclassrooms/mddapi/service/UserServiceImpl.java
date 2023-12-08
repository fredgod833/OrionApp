package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.model.Post;
import com.openclassrooms.mddapi.model.Subject;
import com.openclassrooms.mddapi.model.Subscription;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.model.dto.UserDto;
import com.openclassrooms.mddapi.repository.PostRepository;
import com.openclassrooms.mddapi.repository.SubscriptionRepository;
import com.openclassrooms.mddapi.repository.UserRepository;
import org.springframework.stereotype.Service;

//Layer interface implementation
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    private final PostRepository postRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final SubjectService subjectService;

    public UserServiceImpl(UserRepository userRepository, PostRepository postRepository, SubscriptionRepository subscriptionRepository, SubjectService subjectService) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.subscriptionRepository = subscriptionRepository;
        this.subjectService = subjectService;
    }
    //Persist a comment to post
    @Override
    public Post commentPost(Post post) {
        if (post == null
        ){
            return null;
        }

        return postRepository.save(post);

    }

    //Persist new user username and email
    @Override
    public User changeUserUsernameAndEmail(UserDto userDto) {
        if (userDto == null){
            return null;
        }
        //Load user
        User user = userRepository.findById(userDto.getId_user()).orElse(null);
        //Set new username
        user.setUsername(userDto.getUsername());
        //Set new email
        user.setEmail(userDto.getEmail());
        return userRepository.save(user);
    }

    //Persist user subscription
    public User subscribe(int id_user, int id_subject){
        //Load user and subject
        User user = userRepository.findById(id_user).orElse(null);
        Subject subject = subjectService.getSubjectById(id_subject);

        //Verify both is not null
        if (user == null || subject == null){
            return null;
        }

        //Verify if user subscription is null and create it
        if (user.getSubscription() == null){
            Subscription subscription = new Subscription();

            subscription.getSubjectList().add(subject);
            user.setSubscription(subscription);

            subscriptionRepository.save(subscription);
        }
        // when user subscription is not null, do not create a new
        Subscription subscription = user.getSubscription();

        subscription.getSubjectList().add(subject);
        user.setSubscription(subscription);
        // save subscription on database
        subscriptionRepository.save(subscription);

      return user;
    }

    // Persist unsubscribe
    public User unsubscribe(int id_user, int id_subject){
        //Load user and subject
        User user = getUserById(id_user);
        Subject subject = subjectService.getSubjectById(id_subject);

        for (Subject subjects: user.getSubscription().getSubjectList()) {

            // verify if subject is in subscription list
            if (subjects.getIdSubject() == subject.getIdSubject()){

                // subscription knows user subscription
                Subscription subscription = user.getSubscription();

                // delete subject subscription on subjectList
                subscription.getSubjectList().remove(subject);

                // save subscription on database
                subscriptionRepository.save(subscription);

                return user;
            }
        }
        return user;
    }

    //Persist deleted user account
    @Override
    public User deleteUserAccount(int id_user) {
        //Load user
        User user = userRepository.findById(id_user).orElse(null);

        if (user == null ){
            return null;
        }

        userRepository.delete(user);

        return user;
    }
    //Load user by its id
    @Override
    public User getUserById(int id_user) {

        try {
            return userRepository.findById(id_user).orElse(null);

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
    //Load user by its email
    //TODO: Change userEmail to user
    public User getByEmail(String email) {

        try {
            //Load user
            User user = userRepository.findByEmail(email);

            if (user == null) {
                return null;
            }
            return user;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
