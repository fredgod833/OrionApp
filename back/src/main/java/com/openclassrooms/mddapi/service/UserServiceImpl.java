package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ResponseEntity<?> user_id(int id_user) {
        User user_identity = new User();

        try {
            user_identity = userRepository.findById(id_user).orElse(null);
            if (user_identity == null) {
                return null;
            }
            return ResponseEntity.ok(user_identity);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public User getByEmail(String email) {

        try {
            User userEmail = userRepository.getByEmail(email).orElse(null);

            if (userEmail == null) {
                return null;
            }
            return userEmail;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
