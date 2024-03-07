package com.openclassrooms.mddapi.services.implementation;

import com.openclassrooms.mddapi.dtos.RegisterDTO;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.repositories.UserRepository;
import com.openclassrooms.mddapi.services.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepo;

    public UserServiceImpl(PasswordEncoder passwordEncoder, UserRepository userRepo) {
        this.passwordEncoder = passwordEncoder;
        this.userRepo = userRepo;
    }

    public void register(RegisterDTO registerDTO) {
        User user = new User(
                registerDTO.getEmail(),
                registerDTO.getUsername(),
                passwordEncoder.encode(registerDTO.getPassword()),
                LocalDateTime.now(),
                LocalDateTime.now());

        userRepo.save(user);
    }

    public List<User> getAll() {
        return userRepo.findAll();
    }

    public User findByEmail(String email) {
        return userRepo.findByEmail(email).orElse(new User());
    }

    public User findById(Integer id) {
        return new User();
    }

    public void delete(Integer id) {}

    public boolean existsByEmail(String email) {
        return userRepo.existsByEmail(email);
    }
}
