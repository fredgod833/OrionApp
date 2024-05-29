package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.models.UserEntity;
import com.openclassrooms.mddapi.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void delete(Long id) {
        this.userRepository.deleteById(id);
    }

    public UserEntity findById(Long id) {
        return this.userRepository.findById(id).orElse(null);
    }
}
