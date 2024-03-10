package com.openclassrooms.mddapi.services.implementation;

import com.openclassrooms.mddapi.dtos.RegisterDTO;
import com.openclassrooms.mddapi.dtos.UpdatedUserDTO;
import com.openclassrooms.mddapi.dtos.UserDTO;
import com.openclassrooms.mddapi.exceptions.ResourceNotFoundException;
import com.openclassrooms.mddapi.exceptions.UserNotFoundException;
import com.openclassrooms.mddapi.models.Theme;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.repositories.ThemeRepository;
import com.openclassrooms.mddapi.repositories.UserRepository;
import com.openclassrooms.mddapi.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepo;
    private final ModelMapper modelMapper;
    private final ThemeRepository themeRepo;

    public UserServiceImpl(PasswordEncoder passwordEncoder, UserRepository userRepo, ModelMapper modelMapper, ThemeRepository themeRepo) {
        this.passwordEncoder = passwordEncoder;
        this.userRepo = userRepo;
        this.modelMapper = modelMapper;
        this.themeRepo = themeRepo;
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

    public UserDTO getUserById(int id) {
        return userRepo.findById(id)
                .map(user -> modelMapper.map(user, UserDTO.class))
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    public UserDTO getUserByEmail(String email) {
        return userRepo.findByEmail(email)
                .map(user -> modelMapper.map(user, UserDTO.class))
                .orElseThrow(() -> new UserNotFoundException(email));
    }

    @Override
    public UpdatedUserDTO updateUser(int userId, UpdatedUserDTO updatedUser) {
        User user = userRepo.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        modelMapper.map(updatedUser, user);
        userRepo.save(user);
        return modelMapper.map(user, UpdatedUserDTO.class);
    }

    public boolean userExists(String email) {
        return userRepo.existsByEmail(email);
    }

    @Override
    public UserDTO subscribeToTheme(int id, String username) {
        Theme theme = themeRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Theme with " + id + " id not found"));

        User user = userRepo.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with " + username + " email not found"));

        if (!user.getSubscriptions().contains(theme)) {
            user.getSubscriptions().add(theme);
            userRepo.save(user);
        }

        return modelMapper.map(user, UserDTO.class);
    }

    @Override
    public UserDTO unsubscribeFromTheme(int id, String username) {
        Theme theme = themeRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Theme with " + id + " id not found"));

        User user = userRepo.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with " + username + " email not found"));

        if (user.getSubscriptions().contains(theme)) {
            user.getSubscriptions().remove(theme);
            userRepo.save(user);
        }

        return modelMapper.map(user, UserDTO.class);
    }
}
