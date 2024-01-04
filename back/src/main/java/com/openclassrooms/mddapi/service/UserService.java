package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.model.Comments;
import com.openclassrooms.mddapi.model.Post;
import com.openclassrooms.mddapi.model.Subject;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.model.dto.UserDto;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Interface to implement
 */
@Service
public interface UserService {
    User getUserById(int id_user);
    User changeUserUsernameAndEmail(UserDto userDto);
    User subscribe(int id_user, Subject subject);
    User getByEmail(String email);
    User unsubscribe(int id_user, Subject subject);
    User deleteUserAccount(int id_user);
    Post commentPost(Post post, Comments comment);

    //TODO: This is a test
    Comments newComments(Comments comments, int id_post);
    List<Comments> getCommentsList();
}
