package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.models.entities.UserEntity;
import com.openclassrooms.mddapi.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void delete(Integer id) {
        this.userRepository.deleteById(id);
    }

    public UserEntity findById(Integer id) {
        return this.userRepository.findById(id).orElse(null);
    }
}
