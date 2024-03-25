package com.openclassrooms.mddapi.services.implementation;

import com.openclassrooms.mddapi.dtos.UserDTO;
import com.openclassrooms.mddapi.exceptions.UserNotFoundException;
import com.openclassrooms.mddapi.models.Theme;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.payload.requests.RegisterRequest;
import com.openclassrooms.mddapi.repositories.UserRepository;
import com.openclassrooms.mddapi.services.ThemeService;
import com.openclassrooms.mddapi.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DuplicateKeyException;
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

    @Override
    public void register(RegisterRequest registerRequest) {
        if (usernameDoesNotExist(registerRequest.getUsername())
                && emailDoesNotExist(registerRequest.getEmail())) {
            User user = modelMapper.map(registerRequest, User.class);

            user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
            userRepo.save(user);
        }
    }

    @Override
    public User getById(int id) {
        return userRepo.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    @Override
    public User getByEmail(String email) {
        return userRepo.findByEmail(email).orElseThrow(() -> new UserNotFoundException(email));
    }

    @Override
    public User getByUsername(String username) {
        return userRepo.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
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
