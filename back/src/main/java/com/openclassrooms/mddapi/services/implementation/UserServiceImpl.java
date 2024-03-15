package com.openclassrooms.mddapi.services.implementation;

import com.openclassrooms.mddapi.dtos.RegisterDTO;
import com.openclassrooms.mddapi.dtos.UserDTO;
import com.openclassrooms.mddapi.models.Theme;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.repositories.UserRepository;
import com.openclassrooms.mddapi.services.ThemeService;
import com.openclassrooms.mddapi.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepo;
    private final ModelMapper modelMapper;
    private final ThemeService themeService;

    public UserServiceImpl(
            PasswordEncoder passwordEncoder,
            UserRepository userRepo,
            ModelMapper modelMapper,
            ThemeService themeService) {
        this.passwordEncoder = passwordEncoder;
        this.userRepo = userRepo;
        this.modelMapper = modelMapper;
        this.themeService = themeService;
    }

    public void register(RegisterDTO registerDTO) {
        if (usernameDoesNotExist(registerDTO.getUsername())
                && emailDoesNotExist(registerDTO.getEmail())) {
            User user = modelMapper.map(registerDTO, User.class);

            user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
            userRepo.save(user);
        }
    }

    public User getById(int id) {
        return userRepo.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User with " + id + " id not found"));
    }

    public User getByEmail(String email) {
        return userRepo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User with " + email + " email not found"));
    }

    @Override
    public User getByUsername(String username) {
        return userRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with " + username + " name not found"));
    }

    @Override
    public void update(int id, UserDTO userDTO) {
        User user = getById(id);

        if (!Objects.equals(user.getEmail(), userDTO.getEmail())) {
            if (emailDoesNotExist(userDTO.getEmail())) {
                user.setEmail(userDTO.getEmail());
            }
        }
        if (!Objects.equals(user.getUsername(), userDTO.getUsername())) {
            if (usernameDoesNotExist(userDTO.getUsername())) {
                user.setUsername(userDTO.getUsername());
            }
        }
        userRepo.save(user);
    }

    @Override
    public boolean emailDoesNotExist(String email) {
        if (userRepo.existsByEmail(email)) {
            throw new DuplicateKeyException("Email already exists");
        }

        return true;
    }

    @Override
    public boolean usernameDoesNotExist(String username) {
        if (userRepo.existsByUsername(username)) {
            throw new DuplicateKeyException("Username already exists");
        }

        return true;
    }

    @Override
    public User subscribeToTheme(int id, String username) {
        return updateSubscriptions(id, username, true);
    }

    @Override
    public User unSubscribeFromTheme(int id, String username) {
        return updateSubscriptions(id, username, false);
    }

    private User updateSubscriptions(int themeId, String email, boolean request) {
        Theme theme = themeService.getById(themeId);
        User user = getByEmail(email);

        if (request) {
            user.getSubscriptions().add(theme);
        } else {
            user.getSubscriptions().removeIf(theme::equals);
        }
        return userRepo.save(user);
    }
}
