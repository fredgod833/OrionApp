package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.model.Post;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.model.dto.UserDto;
import org.springframework.stereotype.Service;

/**
 * Interface to implement
 */
@Service
public interface UserService {
    User getUserById(int id_user);
    User changeUserUsernameAndEmail(UserDto userDto);
    User subscribe(int id_user, int id_subject);
    User getByEmail(String email);
    User unsubscribe(int id_user, int id_subject);
    User deleteUserAccount(int id_user);
    Post commentPost(Post post);
}
