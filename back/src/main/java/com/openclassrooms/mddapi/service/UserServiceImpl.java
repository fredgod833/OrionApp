package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.model.*;
import com.openclassrooms.mddapi.model.dto.UserDto;
import com.openclassrooms.mddapi.repository.*;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Layer interface implementation
 */
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final SubjectService subjectService;

    private final SubjectRepository subjectRepository;

    //TODO: This is a test
    private final CommentsRepository commentsRepository;

    public UserServiceImpl(UserRepository userRepository, PostRepository postRepository, SubscriptionRepository subscriptionRepository, SubjectService subjectService, SubjectRepository subjectRepository, CommentsRepository commentsRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.subscriptionRepository = subscriptionRepository;
        this.subjectService = subjectService;
        this.subjectRepository = subjectRepository;
        this.commentsRepository = commentsRepository;
    }

    /**
     * Persist a comment to post
     * @param post entry validation
     * @return post
     */
    @Override
    public Post commentPost(Post post, Comments comment) {

        Comments comments = new Comments();
        comments.setComment(comment.getComment());

        post.getComments().add(comments);

        return post;

    }

    /**
     * Persist new user username and email
     * @param userDto entry validation
     * @return user
     */
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

    /**
     * Persist user subscription
     * @param id_user entry validation
     * @param subject entry validation
     * @return user
     */
    public User subscribe(int id_user, Subject subject){
        //Load user
        User user = userRepository.findById(id_user).orElse(null);

        //Verify both is not null
        if (user == null || subject == null){
            return null;
        }

        //Verify if user subscription is null and create it
        if (user.getSubscription() == null){
            Subscription subscription = new Subscription();

            subscription.getSubjectList().add(subject);

            user.setSubscription(subscription);

            userRepository.save(user);
            subjectRepository.save(subject);
            //subscriptionRepository.save(subscription);
        }
        // when user subscription is not null, do not create a new
        Subscription subscription = user.getSubscription();

        subscription.getSubjectList().add(subject);
        user.setSubscription(subscription);
        // save subscription on database
        //subscriptionRepository.save(subscription);
        userRepository.save(user);
        subjectRepository.save(subject);
        //subscriptionRepository.save(subscription);
      return user;
    }

    // Persist unsubscribe

    /**
     * Persist unsubscribe
     * @param id_user entry validation
     * @param subject entry validation
     * @return user
     */
    public User unsubscribe(int id_user, Subject subject){
        //Load user and subject
        User user = userRepository.findById(id_user).orElseThrow();

        for (Subject subjects: user.getSubscription().getSubjectList()) {

            // verify if subject is in subscription list
            if (subjects.getIdSubject() == subject.getIdSubject()){

                // Remove subject from user subscriptions
                user.getSubscription().getSubjectList().remove(subjects); 

                // save user
                userRepository.save(user);
                // save unsubscribed subject
                subjectRepository.save(subject);
                return user;
            }
        }
        return user;
    }

    /**
     * Persist deleted user account
     * @param id_user entry validation
     * @return user
     */
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

    /**
     * Load user by its id
     * @param id_user entry validation
     * @return user
     */
    @Override
    public User getUserById(int id_user) {

        try {
            return userRepository.findById(id_user).orElse(null);

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * Load user by its email
     * @param email entry validation
     * @return user
     */
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

    //TODO: This is a test
    public Comments newComments(Comments comments, int id_post){

        Comments comment = new Comments();
        comment.setId_comments(comments.getId_comments());
        comment.setComment(comments.getComment());
        comment.setPost(id_post);
        comment.setAuthor(comments.getAuthor());
        return commentsRepository.save(comment);
    }
    public List<Comments> getCommentsList(){
        return commentsRepository.findAll();
    }
}
